package com.orderpicker.order.domain.repository;

import com.orderpicker.order.domain.model.Order;
import com.orderpicker.order.infrastructure.dto.OrderInformation;
import com.orderpicker.order.infrastructure.dto.Orders;
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
    Page<OrderInformation> findByUser(Long id, Pageable pageable);

    @Query("""
            SELECT o.id as id, u.fullName as user, u.email as userEmail, o.orderDescription as orderDescription, o.isDelivered as isDelivered,
            o.totalPrice as totalPrice, o.createdAt as createdAt from orders o
            INNER JOIN users u on o.client = u.id
            """)
    Page<Orders> getAll(Pageable pageable);

    @Query("""
            SELECT o.id as id, u.fullName as user, u.email as userEmail, o.orderDescription as orderDescription, o.isDelivered as isDelivered,
            o.totalPrice as totalPrice, o.createdAt as createdAt from orders o
            INNER JOIN users u on o.client = u.id
            where isDelivered = ?1
            """)
    Page<Orders> getAllDelivered(boolean delivered, Pageable pageable);

    @Query("""
            SELECT o.id as id, u.fullName as user, u.email as userEmail, o.orderDescription as orderDescription, o.isDelivered as isDelivered,
            o.totalPrice as totalPrice, o.createdAt as createdAt from orders o
            INNER JOIN users u on o.client = u.id
            where o.id = ?1
            """)
    Orders getOneById(Long id);

    @Query("""
            SELECT o.id as id, o.orderDescription as orderDescription, o.isDelivered as isDelivered,
            o.totalPrice as totalPrice, o.createdAt as createdAt from orders o
            inner join users u on o.client = u.id            
            where u.id = ?1 AND o.id = ?2
            """)
    OrderInformation getOneByIdAndUser(Long idUser, Long id);
}
