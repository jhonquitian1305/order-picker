package com.orderpicker.order.infrastructure.controller;

import com.orderpicker.order.application.mapper.MapperOrder;
import com.orderpicker.order.infrastructure.dto.OrderDTO;
import com.orderpicker.order.infrastructure.response.OrderDTOResponse;
import com.orderpicker.order.infrastructure.service.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.orderpicker.order.infrastructure.constants.OrderEndpointsConstants.ENDPOINT_ORDERS;

@RestController
@RequestMapping(ENDPOINT_ORDERS)
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class OrderController {

    private final @NonNull OrderService orderService;

    private final @NonNull MapperOrder mapperOrder;

    @PostMapping
    ResponseEntity<OrderDTOResponse> saveOne(@RequestBody OrderDTO orderDTO){
        return new ResponseEntity<>(this.mapperOrder.mapOrderDTOResponse(this.orderService.createOrder(orderDTO)), HttpStatus.CREATED);
    }
}
