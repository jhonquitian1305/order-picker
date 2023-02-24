package com.orderpicker.delivery.domain.model;

import com.orderpicker.order.domain.model.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long delivery;

    @Column(name = "total_cost", nullable = false)
    @PositiveOrZero
    private double totalCost;

    @Column(name = "is_completed")
    @Value(value = "false")
    private boolean isCompleted;

    @Column(name = "is_payed")
    @Value(value = "false")
    private boolean isPayed;

    @Column(nullable = false)
    @PositiveOrZero
    private double off;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
}
