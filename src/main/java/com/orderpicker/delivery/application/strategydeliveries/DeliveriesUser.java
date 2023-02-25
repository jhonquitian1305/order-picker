package com.orderpicker.delivery.application.strategydeliveries;

import com.orderpicker.delivery.domain.repository.DeliveryRepository;
import com.orderpicker.delivery.infrastructure.dto.DeliveryInformation;
import com.orderpicker.user.infrastructure.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveriesUser implements DeliveriesStrategy {

    private final @NonNull DeliveryRepository deliveryRepository;

    private final @NonNull UserService userService;

    @Override
    public Page<DeliveryInformation> findDeliveries(Pageable pageable, String idCondition) {
        this.userService.getByDni(idCondition);
        return this.deliveryRepository.getAllByUser(pageable, idCondition);
    }
}
