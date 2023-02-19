package com.orderpicker.order.infrastructure.response;

import com.orderpicker.order.infrastructure.dto.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersResponse {
    private List<Orders> content;
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private  boolean lastOne;
}
