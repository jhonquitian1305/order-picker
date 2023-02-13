package com.orderpicker.product.infrastructure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.orderpicker.product.infrastructure.constants.ProductEndpointsConstants.ENDPOINT_PRODUCTS;

@RestController
@RequestMapping(ENDPOINT_PRODUCTS)
public class ProductController {
}
