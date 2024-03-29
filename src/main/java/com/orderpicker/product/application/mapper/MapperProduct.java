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
                .imageUrl(productDTO.getImageUrl())
                .build();
    }

    public ProductDTO mapProductDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .amount(product.getAmount())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public Product updateProduct(Product product, ProductDTO productDTO){
        product.setName(productDTO.getName());
        product.setAmount(productDTO.getAmount());
        product.setPrice(product.getPrice());
        product.setImageUrl(productDTO.getImageUrl());

        return product;
    }

    public Product showProductOrder(Product productFound, Product productOrder){
        return Product.builder()
                .id(productFound.getId())
                .name(productFound.getName())
                .amount(productOrder.getAmount())
                .price(productFound.getPrice() * productOrder.getAmount())
                .createdAt(productFound.getCreatedAt())
                .updateAt(productFound.getUpdateAt())
                .build();
    }
}
