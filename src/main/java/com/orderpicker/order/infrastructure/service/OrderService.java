package com.orderpicker.order.infrastructure.service;

import com.orderpicker.delivery.domain.model.Delivery;
import com.orderpicker.order.domain.model.Order;
import com.orderpicker.order.infrastructure.dto.OrderDTO;
import com.orderpicker.order.infrastructure.dto.OrderInformation;
import com.orderpicker.order.infrastructure.dto.Orders;
import com.orderpicker.order.infrastructure.response.OrderUserResponse;
import com.orderpicker.order.infrastructure.response.OrdersResponse;

public interface OrderService {
    Order createOrder(Long id, OrderDTO orderDTO);
    OrderUserResponse getAllByClient(Long idUser, int numberPage, int pageSize, String sortBy, String sortDir);
    OrdersResponse getAll(String delivered, int numberPage, int pageSize, String sortBy, String sortDir);
    Orders getOneById(Long id);
    OrderInformation getOneByIdAndUser(Long idUser, Long id);
    Order markAsDelivered(Order order);
    Order getOneByIdInDelivery(Long id);
    void setDelivery(Order order, Delivery delivery);
    void validateUserRequestById(Long idUser, String userEmail);
}
