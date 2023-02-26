package com.orderpicker.delivery.infrastructure.dto;

import java.util.List;

public interface DeliveryInformation {
    Long getDelivery();
    List<String> getOrderDescription();
    Double getTotalCost();
    boolean getIsCompleted();
    boolean getIsPayed();
    Double getOff();
    String getUserName();
    String getUserEmail();
    String getUserAddress();
    String getUserPhone();
}
