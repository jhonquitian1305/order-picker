package com.orderpicker.order.application.mapper;

import com.orderpicker.order.domain.model.Order;
import com.orderpicker.order.infrastructure.dto.OrderDTO;
import com.orderpicker.order.infrastructure.response.OrderDTOResponse;
import com.orderpicker.user.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class MapperOrder {
    public Order createOrder(OrderDTO orderDTO, User client){
        return Order.builder()
                .id(orderDTO.getId())
                .orderDescription(orderDTO.getOrderDescription())
                .totalPrice(orderDTO.getTotalPrice())
                .isDelivered(false)
                .client(client)
                .build();
    }

    public OrderDTOResponse mapOrderDTOResponse(Order order){
        return OrderDTOResponse.builder()
                .id(order.getId())
                .orderDescription(order.getOrderDescription())
                .totalPrice(order.getTotalPrice())
                .isDelivered(order.isDelivered())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
