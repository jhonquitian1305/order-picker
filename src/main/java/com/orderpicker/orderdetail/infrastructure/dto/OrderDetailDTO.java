package com.orderpicker.orderdetail.infrastructure.dto;

import com.orderpicker.product.infrastructure.dto.ProductDetailsDTO;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderDetailDTO {
    private Long idOrder;
    private Double totalPrice;
    private String userName;
    private Timestamp createAt;
    private List<ProductDetailsDTO> products;
    private boolean isDelivered;
}
