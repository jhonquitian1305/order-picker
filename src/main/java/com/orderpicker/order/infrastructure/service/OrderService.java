package com.orderpicker.order.infrastructure.service;

import com.orderpicker.order.domain.model.Order;
import com.orderpicker.order.infrastructure.dto.OrderDTO;

public interface OrderService {
    Order createOrder(Long id, OrderDTO orderDTO);
}
