package com.orderpicker.product.domain.repository;

import com.orderpicker.product.domain.model.Product;
import com.orderpicker.product.infrastructure.dto.ProductDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    @Query("""
    select p.id as id, p.name as name, p.price as unitPrice from products p
    inner join OrderDetail od ON od.product.id = p.id
    inner join orders o on od.order.id = o.id
    where o.id = :idOrder
    """)
    List<ProductDetails> findDetailsProductsByIdOrder(Long idOrder);

    Page<Product> findAllByAmountGreaterThan(Pageable pageable, Integer amount);
}
