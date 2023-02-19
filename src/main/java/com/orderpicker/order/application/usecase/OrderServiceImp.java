package com.orderpicker.order.application.usecase;

import com.orderpicker.order.application.exception.OrderBadRequestException;
import com.orderpicker.order.application.mapper.MapperOrder;
import com.orderpicker.order.domain.model.Order;
import com.orderpicker.order.domain.repository.OrderRepository;
import com.orderpicker.order.infrastructure.dto.OrderDTO;
import com.orderpicker.order.infrastructure.dto.OrderUser;
import com.orderpicker.order.infrastructure.response.OrderUserResponse;
import com.orderpicker.order.infrastructure.service.OrderService;
import com.orderpicker.product.domain.model.Product;
import com.orderpicker.product.infrastructure.dto.ProductDTO;
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

        Page<OrderUser> ordersFound = this.orderRepository.findByUser(userFound.getId(), pageable);

        return OrderUserResponse.builder()
                .content(ordersFound.getContent())
                .pageNumber(ordersFound.getNumber())
                .pageSize(ordersFound.getSize())
                .totalElements(ordersFound.getTotalElements())
                .totalPages(ordersFound.getTotalPages())
                .lastOne(ordersFound.isLast())
                .build();
    }

    protected List<Product> searchProducts(List<Product> products){
        List<Product> productsFound = new ArrayList<>();
        for(Product product:products) productsFound.add(this.productService.getByName(product.getName()));
        return productsFound;
    }

    protected void registerChangeProduct(OrderDTO orderDTO, List<Product> productsFound){
        List<String> productsDescription = new ArrayList<>();
        for(Product productFound:productsFound){
            orderDTO.getProducts().stream().forEach(product -> {
                if(product.getName().equals(productFound.getName())){
                    this.productService.registerProductOut(productFound, product.getAmount());
                    orderDTO.setTotalPrice(orderDTO.getTotalPrice() + this.productService.getTotalPriceProduct(productFound, product.getAmount()));
                    productsDescription.add(String.format("Product: %s, amount: %s, unit price: %s, total price product: %s", product.getName(), product.getAmount(), productFound.getPrice(), this.productService.getTotalPriceProduct(productFound, product.getAmount())));
                }
                orderDTO.setOrderDescription(productsDescription);
            });
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