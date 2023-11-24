package com.orderpicker.product.infrastructure.service;

import com.orderpicker.product.domain.model.Product;
import com.orderpicker.product.infrastructure.dto.ProductDTO;
import com.orderpicker.product.infrastructure.dto.ProductDetails;
import com.orderpicker.product.infrastructure.response.ProductResponse;

import java.util.List;

public interface ProductService {
    Product saveOne(ProductDTO productDTO);
    Product getById(Long id);
    ProductResponse getAll(String userEmail, int numberPage, int pageSize, String sortBy, String sortDir);
    Product getByName(String name);
    Product updateOneById(Long id, ProductDTO productDTO);
    Product updatePrice(Long id, Double price);
    Product registerProductEntry(String name, int amount);
    Product registerProductOut(Product product, int amount);
    Double getTotalPriceProduct(Product product, int amount);
    void deleteOneById(Long id);
    void verifyAmountInOneOrder(List<Product> products, List<Product> productsDTO);
    List<ProductDetails> findDetailsProductsByIdOrder(Long idOrder);
}
