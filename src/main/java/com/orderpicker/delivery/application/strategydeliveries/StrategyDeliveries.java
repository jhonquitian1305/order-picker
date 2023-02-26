package com.orderpicker.delivery.application.strategydeliveries;

import com.orderpicker.delivery.domain.repository.DeliveryRepository;
import com.orderpicker.delivery.infrastructure.dto.DeliveryInformation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StrategyDeliveries implements DeliveriesStrategy {

    private final @NonNull DeliveryRepository deliveryRepository;

    @Override
    public Page<DeliveryInformation> findDeliveries(Pageable pageable, String idCondition) {
        return this.deliveryRepository.getAll(pageable);
    }
}
