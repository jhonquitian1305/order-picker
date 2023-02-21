package com.orderpicker.delivery.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DeliveryBadRequestException extends RuntimeException {
    public DeliveryBadRequestException(String message) {
        super(message);
    }
}
