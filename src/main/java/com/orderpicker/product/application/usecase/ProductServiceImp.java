package com.orderpicker.product.application.usecase;

import com.orderpicker.product.application.exception.ProductBadRequestException;
import com.orderpicker.product.domain.model.Product;
import com.orderpicker.product.domain.repository.ProductRepository;
import com.orderpicker.product.infrastructure.service.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class ProductServiceImp implements ProductService {

    private final @NonNull ProductRepository productRepository;

    protected void findByName(String name){
        Optional<Product> productFound = this.productRepository.findByName(name);
        if(productFound.isEmpty()){
            throw new ProductBadRequestException("Product with name %s already exists".formatted(name));
        }
    }
}
