package com.orderpicker.delivery.application.usecase;

import com.orderpicker.delivery.application.exception.DeliveryBadRequestException;
import com.orderpicker.delivery.application.mapper.MapperDelivery;
import com.orderpicker.delivery.domain.model.Delivery;
import com.orderpicker.delivery.domain.repository.DeliveryRepository;
import com.orderpicker.delivery.infrastructure.dto.DeliveryDTO;
import com.orderpicker.delivery.infrastructure.service.DeliveryService;
import com.orderpicker.order.domain.model.Order;
import com.orderpicker.order.infrastructure.service.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryServiceImp implements DeliveryService {

    private final @NonNull DeliveryRepository deliveryRepository;

    private final @NonNull OrderService orderService;

    private final @NonNull MapperDelivery mapperDelivery;

    @Override
    public Delivery createOne(DeliveryDTO deliveryDTO) {
        Order orderFound = this.orderService.getOneByIdInDelivery(deliveryDTO.getOrder());

        if(orderFound.getDelivery() != null){
            throw new DeliveryBadRequestException(String.format("Order with id %s already is ordered", orderFound.getId()));
        }

        this.setTotalCost(deliveryDTO, orderFound);

        Delivery delivery = this.deliveryRepository.save(this.mapperDelivery.createDelivery(deliveryDTO, orderFound));

        this.orderService.setDelivery(orderFound, delivery);

        return delivery;
    }

    protected void setTotalCost(DeliveryDTO deliveryDTO, Order order){
        deliveryDTO.setTotalCost(order.getTotalPrice() - (order.getTotalPrice() * (deliveryDTO.getOff()/100)));
    }
}
