package com.orderpicker.product.infrastructure.service;

import com.orderpicker.product.domain.model.Product;
import com.orderpicker.product.infrastructure.dto.ProductDTO;
import com.orderpicker.product.infrastructure.response.ProductResponse;

public interface ProductService {
    Product saveOne(ProductDTO productDTO);
    Product getById(Long id);
    ProductResponse getAll(int numberPage, int pageSize, String sortBy, String sortDir);
    Product getByName(String name);
}
