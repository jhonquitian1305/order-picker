package com.orderpicker.order.infrastructure.service;

import com.orderpicker.order.domain.model.Order;
import com.orderpicker.order.infrastructure.dto.OrderDTO;
import com.orderpicker.order.infrastructure.response.OrderUserResponse;

public interface OrderService {
    Order createOrder(Long id, OrderDTO orderDTO);
    OrderUserResponse getAllByClient(Long idUser, int numberPage, int pageSize, String sortBy, String sortDir);
}
