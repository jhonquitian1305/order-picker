package com.orderpicker.delivery.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {
    private Long delivery;

    @NotNull(message = "order field must have one value")
    private Long order;

    private double totalCost;

    private boolean isCompleted;

    private boolean isPayed;

    @NotNull(message = "off field couldn't be null")
    @PositiveOrZero
    private double off;

    private Timestamp createdAt;
}
