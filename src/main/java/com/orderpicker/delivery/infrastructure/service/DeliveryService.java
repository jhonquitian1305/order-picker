package com.orderpicker.delivery.infrastructure.service;

import com.orderpicker.delivery.domain.model.Delivery;
import com.orderpicker.delivery.infrastructure.dto.DeliveryDTO;
import com.orderpicker.delivery.infrastructure.dto.DeliveryInformation;
import com.orderpicker.delivery.infrastructure.response.DeliveryResponse;

public interface DeliveryService {
    Delivery createOne(DeliveryDTO deliveryDTO);
    DeliveryResponse getAllDeliveries(String modelCondition, String idCondition, int numberPage, int pageSize, String sortBy, String sortDir);
    DeliveryInformation getOneById(Long id);
    void orderDelivered(Long id);
}
