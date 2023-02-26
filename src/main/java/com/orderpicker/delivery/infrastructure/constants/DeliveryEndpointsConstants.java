package com.orderpicker.delivery.infrastructure.constants;

public class DeliveryEndpointsConstants {
    public static final String ENDPOINT_DELIVERIES="/api/order-picker/deliveries";
    public static final String ENDPOINT_DELIVERIES_ID="/{id}";
    public static final String ENDPOINT_DELIVERED="/delivered";
    public static final String ENDPOINT_DELIVERIES_DELIVERED=ENDPOINT_DELIVERIES_ID+ENDPOINT_DELIVERED;
}
