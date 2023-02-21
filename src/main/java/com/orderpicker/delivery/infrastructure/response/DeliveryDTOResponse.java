package com.orderpicker.delivery.infrastructure.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTOResponse {
    private Long delivery;
    private List<String> orderDescription;
    private double totalCost;
    private boolean isCompleted;
    private boolean isPayed;
    private double off;
    private String userName;
    private String userEmail;
    private String userAddress;
    private String userPhone;
}
