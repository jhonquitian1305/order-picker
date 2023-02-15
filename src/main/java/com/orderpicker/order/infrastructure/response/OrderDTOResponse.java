package com.orderpicker.order.infrastructure.response;

import com.orderpicker.product.domain.model.Product;
import com.orderpicker.user.domain.model.User;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTOResponse {
    private Long id;
    private String description;
    private double totalPrice;
    private boolean isDelivered;
    private User client;
    private List<Product> products;
    private Timestamp createdAt;
}
