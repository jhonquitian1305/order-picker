package com.orderpicker.order.infrastructure.dto;

public interface Orders extends OrderInformation {
    String getUser();
    String getUserEmail();
    String getUserAddress();
    String getUserPhone();
}
