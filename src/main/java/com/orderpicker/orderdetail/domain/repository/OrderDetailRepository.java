package com.orderpicker.orderdetail.domain.repository;

import com.orderpicker.orderdetail.domain.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("""
        select od.amount from OrderDetail od
        where od.product.id = :productId and
        od.order.id = :orderId
    """)
    int findAmountByProductIdAndOrderId(Long productId, Long orderId);
}
