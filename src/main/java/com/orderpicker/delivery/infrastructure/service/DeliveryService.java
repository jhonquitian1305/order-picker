package com.orderpicker.delivery.infrastructure.service;

import com.orderpicker.delivery.domain.model.Delivery;
import com.orderpicker.delivery.infrastructure.dto.DeliveryDTO;

public interface DeliveryService {
    Delivery createOne(DeliveryDTO deliveryDTO);
}
