package com.orderpicker.delivery.infrastructure.response;

import com.orderpicker.delivery.infrastructure.dto.DeliveryInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryResponse {
    List<DeliveryInformation> content;
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private  boolean lastOne;
}
