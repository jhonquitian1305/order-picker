package com.orderpicker.order.infrastructure.dto;

public interface Orders extends OrderInformation {
    Long getUserId();
    String getUser();
    String getUserEmail();
    String getUserAddress();
    String getUserPhone();
}
