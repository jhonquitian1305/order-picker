package com.orderpicker.orderdetail.application.usecase;

import com.orderpicker.orderdetail.domain.model.OrderDetail;
import com.orderpicker.orderdetail.domain.repository.OrderDetailRepository;
import com.orderpicker.orderdetail.infrastructure.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImp implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public void createOne(OrderDetail orderDetail){
        this.orderDetailRepository.save(orderDetail);
    }

    @Override
    public int findAmountByProductIdAndOrderId(Long idProduct, Long idOrder) {
        return this.orderDetailRepository.findAmountByProductIdAndOrderId(idProduct, idOrder);
    }
}
