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

    private List<String> orderDescription;

    private double totalPrice;

    private boolean isDelivered;

    @NotNull(message = "products field couldn't be null")
    @NotEmpty(message = "products field couldn't be empty")
    @Size(min = 1, max = 25)
    private List<Product> products;

    private Timestamp createdAt;
}
