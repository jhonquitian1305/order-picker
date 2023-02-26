package com.orderpicker.delivery.application.strategydeliveries;

import com.orderpicker.delivery.domain.repository.DeliveryRepository;
import com.orderpicker.user.infrastructure.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveriesContext {

    private final @NonNull DeliveryRepository deliveryRepository;

    private final @NonNull UserService userService;

    public DeliveriesStrategy loadDeliveriesStrategy(String modelCondition){
        switch (modelCondition){
            case "USER":
                return new DeliveriesUser(this.deliveryRepository, this.userService);
            default:
                return new StrategyDeliveries(this.deliveryRepository);
        }
    }
}
