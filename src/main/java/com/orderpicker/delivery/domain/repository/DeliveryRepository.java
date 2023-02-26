package com.orderpicker.delivery.domain.repository;

import com.orderpicker.delivery.domain.model.Delivery;
import com.orderpicker.delivery.infrastructure.dto.DeliveryInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    @Query("""
            SELECT d.delivery AS delivery, o.orderDescription AS orderDescription, d.totalCost AS totalCost,
            d.isCompleted AS isCompleted, d.isPayed AS isPayed, d.off AS off, u.fullName AS userName, u.email AS userEmail,
            u.address AS userAddress, u.phone AS userPhone
            FROM deliveries AS d
            INNER JOIN orders AS o ON o.id = d.order
            INNER JOIN users AS u ON u.id = o.client
            """)
    Page<DeliveryInformation> getAll(Pageable pageable);

    @Query("""
            SELECT d.delivery AS delivery, o.orderDescription AS orderDescription, d.totalCost AS totalCost,
            d.isCompleted AS isCompleted, d.isPayed AS isPayed, d.off AS off, u.fullName AS userName, u.email AS userEmail,
            u.address AS userAddress, u.phone AS userPhone
            FROM deliveries AS d
            INNER JOIN orders AS o ON o.id = d.order
            INNER JOIN users AS u ON u.id = o.client
            WHERE d.id = ?1
            """)
    DeliveryInformation getOneById(Long id);

    @Query("""
            SELECT d.delivery AS delivery, o.orderDescription AS orderDescription, d.totalCost AS totalCost,
            d.isCompleted AS isCompleted, d.isPayed AS isPayed, d.off AS off, u.fullName AS userName, u.email AS userEmail,
            u.address AS userAddress, u.phone AS userPhone
            FROM deliveries AS d
            INNER JOIN orders AS o ON o.id = d.order
            INNER JOIN users AS u ON u.id = o.client
            WHERE u.dni = ?1
            """)
    Page<DeliveryInformation> getAllByUser(Pageable pageable, String idCondition);
}
