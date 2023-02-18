package com.orderpicker.order.infrastructure.dto;

import java.sql.Timestamp;
import java.util.List;

public interface OrderUser {
    Long getId();
    List<String> getOrderDescription();
    String getTotalPrice();
    Timestamp getCreatedAt();
    boolean getIsDelivered();
}
