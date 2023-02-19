package com.orderpicker.order.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderBadRequestException extends RuntimeException{
    public OrderBadRequestException(String message) {
        super(message);
    }
}
