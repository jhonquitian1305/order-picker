package com.orderpicker.product.infrastructure.constants;

public class ProductEndpointsConstants {
    public static final String ENDPOINT_PRODUCTS="/api/order-picker/products";
    public static final String ENDPOINT_PRODUCT_ID="/{id}";
    public static final String ENDPOINT_PRODUCT_NAME="name/{name}";
    public static final String ENDPOINT_PRODUCT_PRICE= ENDPOINT_PRODUCT_ID + "/price" ;
    public static final String ENDPOINT_PRODUCT_AMOUNT= ENDPOINT_PRODUCT_NAME + "/amount";
}
