package com.orderpicker.delivery.application.usecase;

import com.orderpicker.delivery.application.exception.DeliveryBadRequestException;
import com.orderpicker.delivery.application.mapper.MapperDelivery;
import com.orderpicker.delivery.application.strategydeliveries.DeliveriesContext;
import com.orderpicker.delivery.application.strategydeliveries.DeliveriesStrategy;
import com.orderpicker.delivery.domain.model.Delivery;
import com.orderpicker.delivery.domain.repository.DeliveryRepository;
import com.orderpicker.delivery.infrastructure.dto.DeliveryDTO;
import com.orderpicker.delivery.infrastructure.dto.DeliveryInformation;
import com.orderpicker.delivery.infrastructure.response.DeliveryResponse;
import com.orderpicker.delivery.infrastructure.service.DeliveryService;
import com.orderpicker.order.domain.model.Order;
import com.orderpicker.order.infrastructure.service.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryServiceImp implements DeliveryService {

    private final @NonNull DeliveryRepository deliveryRepository;

    private final @NonNull OrderService orderService;

    private final @NonNull MapperDelivery mapperDelivery;

    private final @NonNull DeliveriesContext deliveriesContext;

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

    @Override
    public DeliveryResponse getAllDeliveries(String modelCondition, String idCondition, int numberPage, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(numberPage, pageSize, sort);

        DeliveriesStrategy deliveriesStrategy = this.deliveriesContext.loadDeliveriesStrategy(modelCondition);

        Page<DeliveryInformation> deliveriesFound = deliveriesStrategy.findDeliveries(pageable, idCondition);

        return DeliveryResponse.builder()
                .content(deliveriesFound.getContent())
                .pageNumber(deliveriesFound.getNumber())
                .pageSize(deliveriesFound.getSize())
                .totalElements(deliveriesFound.getTotalElements())
                .totalPages(deliveriesFound.getTotalPages())
                .lastOne(deliveriesFound.isLast())
                .build();
    }

    @Override
    public DeliveryInformation getOneById(Long id) {
        return this.deliveryRepository.getOneById(id);
    }

    protected void setTotalCost(DeliveryDTO deliveryDTO, Order order){
        deliveryDTO.setTotalCost(order.getTotalPrice() - (order.getTotalPrice() * (deliveryDTO.getOff()/100)));
    }
}
