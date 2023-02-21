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
}
