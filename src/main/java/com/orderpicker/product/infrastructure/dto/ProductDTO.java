package com.orderpicker.product.infrastructure.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    @NotNull(message = "The name field couldn't be null")
    @NotEmpty(message = "The name field couldn't be empty")
    @Size(min = 5, max = 100)
    private String name;

    @NotNull(message = "The amount field couldn't be null")
    @PositiveOrZero(message = "The amount field must be greater than or equal to 0")
    private int amount;

    @NotNull(message = "The price field couldn't be null")
    @Positive(message = "The price field must be greater than 0")
    private Double price;
}
