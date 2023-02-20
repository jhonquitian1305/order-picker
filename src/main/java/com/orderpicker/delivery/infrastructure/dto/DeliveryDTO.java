package com.orderpicker.delivery.infrastructure.dto;

import com.orderpicker.order.domain.model.Order;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {
    private Long delivery;

    @NotNull(message = "order field must have one value")
    @NotEmpty(message = "order field must have one value")
    private Long order;

    private double totalCost;

    private boolean isCompleted;

    private boolean isPayed;

    @NotNull(message = "off field couldn't be null")
    @NotEmpty(message = "off field couldn't be empty")
    @Positive
    private double off;
}