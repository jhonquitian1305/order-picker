package com.orderpicker.delivery.domain.model;

import com.orderpicker.order.domain.model.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

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
    @Positive
    private double totalCost;

    @Column(name = "is_completed")
    @Value(value = "false")
    private boolean isCompleted;

    @Column(name = "is_payed")
    @Value(value = "false")
    private boolean isPayed;

    @Column(nullable = false)
    private double off;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
