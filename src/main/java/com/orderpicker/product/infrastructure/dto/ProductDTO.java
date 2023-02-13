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
    @Min(message = "The name field must have at least 5 characters", value = 5)
    @Max(message = "The name field must have a maximum of 100 characters", value = 100)
    private String name;

    @NotNull(message = "The amount field couldn't be null")
    @NotEmpty(message = "The amount field couldn't be empty")
    @Positive(message = "The amount field must be greater than 0")
    private int amount;
}
