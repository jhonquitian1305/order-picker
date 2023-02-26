package com.orderpicker.delivery.application.mapper;

import com.orderpicker.delivery.domain.model.Delivery;
import com.orderpicker.delivery.infrastructure.dto.DeliveryDTO;
import com.orderpicker.delivery.infrastructure.response.DeliveryDTOResponse;
import com.orderpicker.order.domain.model.Order;
import org.springframework.stereotype.Component;

@Component
public class MapperDelivery {
    public Delivery createDelivery(DeliveryDTO deliveryDTO, Order order){
        return Delivery.builder()
                .delivery(deliveryDTO.getDelivery())
                .totalCost(deliveryDTO.getTotalCost())
                .off(deliveryDTO.getOff())
                .order(order)
                .build();
    }

    public DeliveryDTOResponse mapDeliveryDTOResponse(Delivery delivery){
        return DeliveryDTOResponse.builder()
                .delivery(delivery.getDelivery())
                .orderDescription(delivery.getOrder().getOrderDescription())
                .totalCost(delivery.getTotalCost())
                .isCompleted(delivery.isCompleted())
                .isPayed(delivery.isPayed())
                .off(delivery.getOff())
                .userName(delivery.getOrder().getClient().getFullName())
                .userEmail(delivery.getOrder().getClient().getEmail())
                .userAddress(delivery.getOrder().getClient().getAddress())
                .userPhone(delivery.getOrder().getClient().getPhone())
                .build();
    }
}
