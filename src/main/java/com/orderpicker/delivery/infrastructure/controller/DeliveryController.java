package com.orderpicker.delivery.infrastructure.controller;

import com.orderpicker.delivery.application.exception.DeliveryBadRequestException;
import com.orderpicker.delivery.application.mapper.MapperDelivery;
import com.orderpicker.delivery.infrastructure.dto.DeliveryDTO;
import com.orderpicker.delivery.infrastructure.dto.DeliveryInformation;
import com.orderpicker.delivery.infrastructure.response.DeliveryDTOResponse;
import com.orderpicker.delivery.infrastructure.response.DeliveryResponse;
import com.orderpicker.delivery.infrastructure.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.orderpicker.delivery.infrastructure.constants.DeliveryEndpointsConstants.*;
import static com.orderpicker.delivery.infrastructure.constants.DeliveryPaginationRequest.*;

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

    @GetMapping
    ResponseEntity<DeliveryResponse> getAllDeliveries(
            @RequestParam(value = "model", defaultValue = DELIVERY_DEFAULT_MODEL, required = false) String model,
            @RequestParam(value = "id", defaultValue = DELIVERY_DEFAULT_ID, required = false) String id,
            @RequestParam(value = "pageNumber", defaultValue = DELIVERY_DEFAULT_NUMBER_PAGE, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DELIVERY_DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DELIVERY_DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DELIVERY_DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        return new ResponseEntity<>(this.deliveryService.getAllDeliveries(model, id, pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_DELIVERIES_ID)
    ResponseEntity<DeliveryInformation> getOneById(@PathVariable("id") Long id){
        return new ResponseEntity<>(this.deliveryService.getOneById(id), HttpStatus.OK);
    }

    @PatchMapping(ENDPOINT_DELIVERIES_DELIVERED)
    ResponseEntity<String> orderDelivered(
            @PathVariable("id") Long id
    ){
        this.deliveryService.orderDelivered(id);
        return new ResponseEntity<>("Delivery has been delivered", HttpStatus.OK);
    }
}
