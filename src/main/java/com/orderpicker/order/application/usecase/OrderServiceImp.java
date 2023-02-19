package com.orderpicker.order.application.usecase;

import com.orderpicker.order.application.exception.OrderBadRequestException;
import com.orderpicker.order.application.exception.OrderNotFoundException;
import com.orderpicker.order.application.mapper.MapperOrder;
import com.orderpicker.order.domain.model.Order;
import com.orderpicker.order.domain.repository.OrderRepository;
import com.orderpicker.order.infrastructure.dto.OrderDTO;
import com.orderpicker.order.infrastructure.dto.OrderInformation;
import com.orderpicker.order.infrastructure.dto.Orders;
import com.orderpicker.order.infrastructure.response.OrderUserResponse;
import com.orderpicker.order.infrastructure.response.OrdersResponse;
import com.orderpicker.order.infrastructure.service.OrderService;
import com.orderpicker.product.domain.model.Product;
import com.orderpicker.product.infrastructure.service.ProductService;
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

@Service
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class OrderServiceImp implements OrderService {

    private final @NonNull OrderRepository orderRepository;

    private final @NonNull UserService userService;

    private final @NonNull ProductService productService;

    private final @NonNull MapperOrder mapperOrder;

    @Override
    public Order createOrder(Long id, OrderDTO orderDTO) {
        User clientFound = this.userService.getById(id);

        this.negativeAmountProduct(orderDTO.getProducts());

        List<Product> productsFound = this.searchProducts(orderDTO.getProducts());

        this.registerChangeProduct(orderDTO, productsFound);

        return this.orderRepository.save(this.mapperOrder.createOrder(orderDTO, clientFound, productsFound));
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
            throw new OrderBadRequestException(String.format("Data url incorrect in %s must be true or false", condition));
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
            throw new OrderNotFoundException(String.format("Order with id %s doesn't exist", id));
        }
        return orderFound;
    }

    @Override
    public OrderInformation getOneByIdAndUser(Long idUser, Long id) {
        this.userService.getById(idUser);
        this.getOneById(id);

        OrderInformation orderUserFound = this.orderRepository.getOneByIdAndUser(idUser, id);
        if(orderUserFound == null){
            throw new OrderNotFoundException(String.format("Order with id %s doesn't belong to user with id %s", id, idUser));
        }

        return orderUserFound;
    }

    @Override
    public Order markAsDelivered(Order order) {
        if(order.isDelivered()){
            throw new OrderBadRequestException(String.format("Order with id %s already delivered", order.getId()));
        }
        order.setDelivered(true);
        return this.orderRepository.save(order);
    }

    protected List<Product> searchProducts(List<Product> products){
        List<Product> productsFound = new ArrayList<>();
        for(Product product:products) productsFound.add(this.productService.getByName(product.getName()));
        return productsFound;
    }

    protected void registerChangeProduct(OrderDTO orderDTO, List<Product> productsFound){
        List<String> productsDescription = new ArrayList<>();
        List<String> uniqueProduct = new ArrayList<>();
        for(Product productFound:productsFound){
            orderDTO.getProducts().stream().forEach(product -> {
                if(product.getName().equals(productFound.getName()) && !uniqueProduct.contains(product.getName())){
                    this.productService.registerProductOut(productFound, product.getAmount());
                    orderDTO.setTotalPrice(orderDTO.getTotalPrice() + this.productService.getTotalPriceProduct(productFound, product.getAmount()));
                    productsDescription.add(String.format("Product: %s, amount: %s, unit price: %s, total price product: %s", product.getName(), product.getAmount(), productFound.getPrice(), this.productService.getTotalPriceProduct(productFound, product.getAmount())));
                    uniqueProduct.add(product.getName());
                }
            });
            orderDTO.setOrderDescription(productsDescription);
        }
    }

    protected void negativeAmountProduct(List<Product> productsDTO){
        for(Product product: productsDTO){
            if(product.getAmount() < 1){
                throw new OrderBadRequestException(String.format("%s product to must be greater than 0", product.getName()));
            }
        }
    }
}
