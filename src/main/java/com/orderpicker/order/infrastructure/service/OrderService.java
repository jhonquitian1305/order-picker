package com.orderpicker.order.infrastructure.service;

import com.orderpicker.delivery.domain.model.Delivery;
import com.orderpicker.order.domain.model.Order;
import com.orderpicker.order.infrastructure.dto.OrderDTO;
import com.orderpicker.order.infrastructure.dto.Orders;
import com.orderpicker.order.infrastructure.response.OrderUserResponse;
import com.orderpicker.order.infrastructure.response.OrdersResponse;
import com.orderpicker.orderdetail.infrastructure.dto.OrderDetailDTO;

public interface OrderService {
    Order getById(Long id);
    Order createOrder(Long id, OrderDTO orderDTO);
    OrderUserResponse getAllByClient(Long idUser, int numberPage, int pageSize, String sortBy, String sortDir);
    OrdersResponse getAll(String delivered, int numberPage, int pageSize, String sortBy, String sortDir);
    Orders getOneById(Long id);
    Orders getOneByIdAndUser(Long idUser, Long id);
    void markAsDelivered(Order order);
    Order getOneByIdInDelivery(Long id);
    void setDelivery(Order order, Delivery delivery);
    void validateUserRequestById(Long idUser, String userEmail);
    void validateRole(String userEmail);
    OrderDetailDTO getOneDetailsById(Long idUser, Long idOrder);
}
