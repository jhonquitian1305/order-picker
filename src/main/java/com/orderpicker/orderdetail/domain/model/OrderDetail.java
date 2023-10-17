package com.orderpicker.orderdetail.domain.model;

import com.orderpicker.order.domain.model.Order;
import com.orderpicker.product.domain.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;
}
