package com.orderpicker.order.infrastructure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.orderpicker.order.infrastructure.constants.OrderEndpointsConstants.ENDPOINT_ORDERS;

@RestController
@RequestMapping(ENDPOINT_ORDERS)
public class OrderController {
}
