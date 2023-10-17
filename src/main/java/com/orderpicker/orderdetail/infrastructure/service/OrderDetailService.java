package com.orderpicker.orderdetail.infrastructure.service;

import com.orderpicker.orderdetail.domain.model.OrderDetail;

public interface OrderDetailService {
    void createOne(OrderDetail orderDetail);
    int findAmountByProductIdAndOrderId(Long idProduct, Long idOrder);
}
