package com.orderpicker.order.application.usecase;

import com.orderpicker.delivery.domain.model.Delivery;
import com.orderpicker.exception.BadRequestException;
import com.orderpicker.exception.NotFoundException;
import com.orderpicker.order.application.mapper.MapperOrder;
import com.orderpicker.order.domain.model.Order;
import com.orderpicker.order.domain.repository.OrderRepository;
import com.orderpicker.order.infrastructure.dto.OrderDTO;
import com.orderpicker.order.infrastructure.dto.OrderInformation;
import com.orderpicker.order.infrastructure.dto.Orders;
import com.orderpicker.order.infrastructure.response.OrderUserResponse;
import com.orderpicker.order.infrastructure.response.OrdersResponse;
import com.orderpicker.order.infrastructure.service.OrderService;
import com.orderpicker.orderdetail.domain.model.OrderDetail;
import com.orderpicker.orderdetail.infrastructure.dto.OrderDetailDTO;
import com.orderpicker.orderdetail.infrastructure.service.OrderDetailService;
import com.orderpicker.product.domain.model.Product;
import com.orderpicker.product.infrastructure.dto.ProductDetails;
import com.orderpicker.product.infrastructure.dto.ProductDetailsDTO;
import com.orderpicker.product.infrastructure.service.ProductService;
import com.orderpicker.rol.Role;
import com.orderpicker.user.domain.model.User;
import com.orderpicker.user.infrastructure.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class OrderServiceImp implements OrderService {

    private final @NonNull OrderRepository orderRepository;

    private final @NonNull UserService userService;

    private final @NonNull ProductService productService;

    private final @NonNull MapperOrder mapperOrder;

    private final OrderDetailService orderDetailServiceImp;

    @Override
    public Order getById(Long id){
        return this.orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order with id %s not found".formatted(id)));
    }

    @Override
    public Order createOrder(Long id, OrderDTO orderDTO) {
        List<OrderDetail> listOrderDetail = new ArrayList<>();
        User clientFound = this.userService.getById(id);

        this.negativeAmountProduct(orderDTO.getProducts());

        List<Product> productsFound = this.searchProducts(orderDTO.getProducts());

        this.registerChangeProduct(orderDTO, productsFound, listOrderDetail);

        Order orderSaved = this.orderRepository.save(this.mapperOrder.createOrder(orderDTO, clientFound));

        this.addDetail(listOrderDetail, orderSaved);

        return orderSaved;
    }

    @Override
    public OrderUserResponse getAllByClient(Long idUser, int numberPage, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(numberPage, pageSize, sort);

        User userFound = this.userService.getById(idUser);

        Page<OrderInformation> ordersFound = this.orderRepository.findByUser(userFound.getId(), pageable);

        return OrderUserResponse.builder()
                .content(ordersFound.getContent())
                .pageNumber(ordersFound.getNumber())
                .pageSize(ordersFound.getSize())
                .totalElements(ordersFound.getTotalElements())
                .totalPages(ordersFound.getTotalPages())
                .lastOne(ordersFound.isLast())
                .build();
    }

    @Override
    public OrdersResponse getAll(String condition, int numberPage, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(numberPage, pageSize, sort);

        Page<Orders> ordersFound;

        if(condition.length() == 0){
            ordersFound = this.orderRepository.getAll(pageable);
        }else if(condition.equalsIgnoreCase("true") || condition.equalsIgnoreCase("false")){
            boolean delivered = Boolean.parseBoolean(condition);
            ordersFound = this.orderRepository.getAllDelivered(delivered, pageable);
        }else{
            throw new BadRequestException(String.format("Data url incorrect in %s must be true or false", condition));
        }

        return OrdersResponse.builder()
                .content(ordersFound.getContent())
                .pageNumber(ordersFound.getNumber())
                .pageSize(ordersFound.getSize())
                .totalElements(ordersFound.getTotalElements())
                .totalPages(ordersFound.getTotalPages())
                .lastOne(ordersFound.isLast())
                .build();
    }

    @Override
    public Orders getOneById(Long id) {
        Orders orderFound = this.orderRepository.getOneById(id);
        if(orderFound == null){
            throw new NotFoundException(String.format("Order with id %s doesn't exist", id));
        }
        return orderFound;
    }

    @Override
    public Orders getOneByIdAndUser(Long idUser, Long id) {
        this.userService.getById(idUser);
        this.getOneById(id);

        Orders orderUserFound = this.orderRepository.getOneByIdAndUser(idUser, id);
        if(orderUserFound == null){
            throw new NotFoundException(String.format("Order with id %s doesn't belong to user with id %s", id, idUser));
        }

        return orderUserFound;
    }

    @Override
    public void markAsDelivered(Order order) {
        if(order.isDelivered()){
            throw new BadRequestException(String.format("Order with id %s already delivered", order.getId()));
        }
        order.setDelivered(true);
        this.orderRepository.save(order);
    }

    @Override
    public Order getOneByIdInDelivery(Long id) {
        Optional<Order> orderFound = this.orderRepository.findById(id);
        if(orderFound.isEmpty()){
            throw new NotFoundException(String.format("Order with id %s doesn't exist", id));
        }
        return orderFound.get();
    }

    @Override
    public void setDelivery(Order order, Delivery delivery) {
        order.setDelivery(delivery);
        this.orderRepository.save(order);
    }

    @Override
    public void validateUserRequestById(Long idUser, String userEmail) {
        this.userService.validateUserRequestById(idUser, userEmail);
    }

    @Override
    public void validateRole(String userEmail) {
        User userFound = this.userService.getByEmail(userEmail);
        if(!userFound.getRole().equals(Role.EMPLOYEE) && !userFound.getRole().equals(Role.ADMIN)){
            throw new NotFoundException("Request not found");
        }
    }

    @Override
    public OrderDetailDTO getOneDetailsById(Long idUser, Long idOrder){
        Orders orderFound = this.getOneByIdAndUser(idUser, idOrder);

        ProductDetailsDTO productDetailsDTO;
        List<ProductDetailsDTO> productsOrdered = new ArrayList<>();
        List<ProductDetails> productsFound = this.productService.findDetailsProductsByIdOrder(idOrder);
        for(ProductDetails productDetail : productsFound){
            int amount = this.orderDetailServiceImp.findAmountByProductIdAndOrderId(productDetail.getId(), idOrder);
            productDetailsDTO = ProductDetailsDTO.builder()
                    .unitPrice(productDetail.getUnitPrice())
                    .name(productDetail.getName())
                    .amount(amount)
                    .build();
            productsOrdered.add(productDetailsDTO);
        }

        return OrderDetailDTO.builder()
                .idOrder(orderFound.getId())
                .totalPrice(orderFound.getTotalPrice())
                .userName(orderFound.getUser())
                .createAt(orderFound.getCreatedAt())
                .products(productsOrdered)
                .isDelivered(orderFound.getIsDelivered())
                .build();
    }

    protected List<Product> searchProducts(List<Product> products){
        List<Product> productsFound = new ArrayList<>();
        for(Product product:products) productsFound.add(this.productService.getByName(product.getName()));
        return productsFound;
    }

    protected void registerChangeProduct(OrderDTO orderDTO, List<Product> productsFound, List<OrderDetail> listOrderDetail){
        List<String> productsDescription = new ArrayList<>();
        List<String> uniqueProduct = new ArrayList<>();
        this.productService.verifyAmountInOneOrder(productsFound, orderDTO.getProducts());
        for(Product productFound:productsFound){
            orderDTO.getProducts().stream().forEach(product -> {
                if(product.getName().equals(productFound.getName()) && !uniqueProduct.contains(product.getName())){
                    this.productService.registerProductOut(productFound, product.getAmount());
                    orderDTO.setTotalPrice(orderDTO.getTotalPrice() + this.productService.getTotalPriceProduct(productFound, product.getAmount()));
                    productsDescription.add(String.format("Product: %s, amount: %s, unit price: %s, total price product: %s", product.getName(), product.getAmount(), productFound.getPrice(), this.productService.getTotalPriceProduct(productFound, product.getAmount())));
                    uniqueProduct.add(product.getName());
                    Product productOrdered = Product.builder()
                            .id(productFound.getId())
                            .amount(product.getAmount())
                            .build();
                    OrderDetail orderDetail = OrderDetail.builder().product(productOrdered).build();
                    listOrderDetail.add(orderDetail);
                }
            });
            orderDTO.setOrderDescription(productsDescription);
        }
    }

    protected void negativeAmountProduct(List<Product> productsDTO){
        for(Product product: productsDTO){
            if(product.getAmount() < 1){
                throw new BadRequestException(String.format("%s product to must be greater than 0", product.getName()));
            }
        }
    }

    private void addDetail(List<OrderDetail> listOrderDetail, Order order) {
        for(OrderDetail orderDetail : listOrderDetail){
            orderDetail.setAmount(orderDetail.getProduct().getAmount());
            orderDetail.setOrder(order);
            this.orderDetailServiceImp.createOne(orderDetail);
        }
    }
}
