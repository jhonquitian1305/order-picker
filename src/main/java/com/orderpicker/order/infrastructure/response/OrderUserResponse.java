package com.orderpicker.order.infrastructure.response;

import com.orderpicker.order.infrastructure.dto.OrderUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderUserResponse {
    private List<OrderUser> content;
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private  boolean lastOne;
}
