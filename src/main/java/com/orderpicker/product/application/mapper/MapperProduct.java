package com.orderpicker.product.application.mapper;

import com.orderpicker.product.domain.model.Product;
import com.orderpicker.product.infrastructure.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class MapperProduct {
    public Product mapProduct(ProductDTO productDTO){
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .amount(productDTO.getAmount())
                .price(productDTO.getPrice())
                .build();
    }

    public ProductDTO mapProductDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .amount(product.getAmount())
                .price(product.getPrice())
                .build();
    }

    public Product updateProduct(Product product, ProductDTO productDTO){
        product.setName(productDTO.getName());
        product.setAmount(productDTO.getAmount());
        product.setPrice(product.getPrice());

        return product;
    }
}
