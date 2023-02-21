package com.orderpicker.delivery.infrastructure.controller;

import com.orderpicker.delivery.application.exception.DeliveryBadRequestException;
import com.orderpicker.delivery.application.mapper.MapperDelivery;
import com.orderpicker.delivery.infrastructure.dto.DeliveryDTO;
import com.orderpicker.delivery.infrastructure.response.DeliveryDTOResponse;
import com.orderpicker.delivery.infrastructure.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.orderpicker.delivery.infrastructure.constants.DeliveryEndpointsConstants.ENDPOINT_DELIVERIES;

@RestController
@RequestMapping(ENDPOINT_DELIVERIES)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryController {

    private final @NonNull DeliveryService deliveryService;

    private final @NonNull MapperDelivery mapperDelivery;

    @PostMapping
    ResponseEntity<DeliveryDTOResponse> createOne(@Valid @RequestBody DeliveryDTO deliveryDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new DeliveryBadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return new ResponseEntity<>(this.mapperDelivery.mapDeliveryDTOResponse(this.deliveryService.createOne(deliveryDTO)), HttpStatus.CREATED);
    }

}
