package com.orderpicker.order.application.usecase;

import com.orderpicker.order.application.mapper.MapperOrder;
import com.orderpicker.order.domain.model.Order;
import com.orderpicker.order.domain.repository.OrderRepository;
import com.orderpicker.order.infrastructure.dto.OrderDTO;
import com.orderpicker.order.infrastructure.service.OrderService;
import com.orderpicker.product.domain.model.Product;
import com.orderpicker.product.infrastructure.service.ProductService;
import com.orderpicker.user.application.mapper.MapperUser;
import com.orderpicker.user.domain.model.User;
import com.orderpicker.user.infrastructure.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final @NonNull MapperUser mapperUser;

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        User clientFound = this.userService.getByDni(orderDTO.getClient());

        User client = this.mapperUser.showUserOrder(clientFound);

        List<Product> productsFound = this.searchProducts(orderDTO.getProducts());

        this.registerChangeProduct(orderDTO, productsFound);

        return this.orderRepository.save(this.mapperOrder.createOrder(orderDTO, client, productsFound));
    }

    protected List<Product> searchProducts(List<Product> products){
        List<Product> productsFound = new ArrayList<>();
        for(Product product:products) productsFound.add(this.productService.getByName(product.getName()));
        return productsFound;
    }

    protected void registerChangeProduct(OrderDTO orderDTO, List<Product> productsFound){
        for(Product productFound:productsFound){
            orderDTO.getProducts().stream().forEach(product -> {
                if(product.getName().equals(productFound.getName())){
                    this.productService.registerProductOut(productFound, product.getAmount());
                    orderDTO.setTotalPrice(orderDTO.getTotalPrice() + this.productService.getTotalPriceProduct(productFound, product.getAmount()));
                }
            });
        }
    }
}
