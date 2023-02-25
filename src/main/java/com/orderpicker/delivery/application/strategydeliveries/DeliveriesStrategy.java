package com.orderpicker.delivery.application.strategydeliveries;

import com.orderpicker.delivery.infrastructure.dto.DeliveryInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveriesStrategy {
    Page<DeliveryInformation> findDeliveries(Pageable pageable, String idCondition);
}
