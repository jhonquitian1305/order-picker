package com.orderpicker.product.infrastructure.dto;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDetailsDTO {
    String name;
    int amount;
    Double unitPrice;
}
