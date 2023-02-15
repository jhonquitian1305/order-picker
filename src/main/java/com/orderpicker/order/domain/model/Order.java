package com.orderpicker.order.domain.model;

import com.orderpicker.product.domain.model.Product;
import com.orderpicker.user.domain.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column(name = "total_cost")
    private double totalCost;

    @Column(name = "is_delivered")
    @Value(value = "false")
    private boolean isDelivered;

    @ManyToOne
    @JoinColumn(name = "client_dni")
    private User client;

    @ManyToMany
    @Column(nullable = false)
    private List<Product> products;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
