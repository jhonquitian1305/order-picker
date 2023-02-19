package com.orderpicker.order.domain.model;

import com.orderpicker.product.domain.model.Product;
import com.orderpicker.user.domain.model.User;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

    @Column(length = 10000)
    @Size(max = 10000)
    private List<String> orderDescription;

    @Column(name = "total_price")
    @Positive
    private double totalPrice;

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
