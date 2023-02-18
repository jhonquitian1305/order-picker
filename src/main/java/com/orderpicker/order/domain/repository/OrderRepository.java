package com.orderpicker.order.domain.repository;

import com.orderpicker.order.domain.model.Order;
import com.orderpicker.order.infrastructure.dto.OrderUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
            SELECT o.id as id, o.orderDescription as orderDescription, o.isDelivered as isDelivered,
            o.totalPrice as totalPrice, o.createdAt as createdAt from orders o
            inner join users u on o.client = u.id            
            where u.id = ?1""")
    Page<OrderUser> findByUser(Long id, Pageable pageable);
}
