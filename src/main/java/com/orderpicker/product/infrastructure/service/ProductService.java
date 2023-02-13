package com.orderpicker.product.infrastructure.service;

import com.orderpicker.product.domain.model.Product;
import com.orderpicker.product.infrastructure.dto.ProductDTO;

public interface ProductService {
    Product saveOne(ProductDTO productDTO);
    Product getById(Long id);
}
