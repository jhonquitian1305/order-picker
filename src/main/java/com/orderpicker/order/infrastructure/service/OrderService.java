package com.orderpicker.order.infrastructure.service;

import com.orderpicker.order.domain.model.Order;
import com.orderpicker.order.infrastructure.dto.OrderDTO;
import com.orderpicker.order.infrastructure.dto.OrderInformation;
import com.orderpicker.order.infrastructure.dto.Orders;
import com.orderpicker.order.infrastructure.response.OrderUserResponse;
import com.orderpicker.order.infrastructure.response.OrdersResponse;

public interface OrderService {
    Order createOrder(Long id, OrderDTO orderDTO);
    OrderUserResponse getAllByClient(Long idUser, int numberPage, int pageSize, String sortBy, String sortDir);
    OrdersResponse getAll(String condition, int numberPage, int pageSize, String sortBy, String sortDir);
    Orders getOneById(Long id);
    OrderInformation getOneByIdAndUser(Long idUser, Long id);
    Order markAsDelivered(Order order);
}
