package com.orderpicker.order.infrastructure.constants;

public class OrderEndpointsConstants {
    public static final String ENDPOINT_ORDERS="/api/order-picker/orders";
    public static final String ENDPOINT_ORDER_USER="user/{idUser}";
    public static final String ENDPOINT_ORDER_ID="/{id}";
    public static final String ENDPOINT_ORDER_USER_ID=ENDPOINT_ORDER_USER+ENDPOINT_ORDER_ID;
}
