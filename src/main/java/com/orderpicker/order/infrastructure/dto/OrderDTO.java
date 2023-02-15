package com.orderpicker.order.infrastructure.dto;

import com.orderpicker.product.domain.model.Product;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;

    @Size(max = 200)
    private String description;

    private double totalPrice;

    private boolean isDelivered;

    @NotNull(message = "client field couldn't be null")
    @NotEmpty(message = "client field couldn't be empty")
    private String client;

    @NotNull(message = "products field couldn't be null")
    @NotEmpty(message = "client field couldn't be empty")
    private List<Product> products;

    private Timestamp createdAt;
}
