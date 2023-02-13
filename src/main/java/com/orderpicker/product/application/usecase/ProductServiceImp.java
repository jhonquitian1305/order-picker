package com.orderpicker.product.application.usecase;

import com.orderpicker.product.application.exception.ProductBadRequestException;
import com.orderpicker.product.application.exception.ProductNotFoundException;
import com.orderpicker.product.application.mapper.MapperProduct;
import com.orderpicker.product.domain.model.Product;
import com.orderpicker.product.domain.repository.ProductRepository;
import com.orderpicker.product.infrastructure.dto.ProductDTO;
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

    private final @NonNull MapperProduct mapperProduct;

    @Override
    public Product saveOne(ProductDTO productDTO) {
        this.findByName(productDTO.getName());

        return this.productRepository.save(this.mapperProduct.mapProduct(productDTO));
    }

    @Override
    public Product getById(Long id) {
        Optional<Product> productFound = this.productRepository.findById(id);
        if(productFound.isEmpty()){
            throw new ProductNotFoundException("Product whit id %s doesn't exist".formatted(id));
        }
        return productFound.get();
    }

    protected void findByName(String name){
        Optional<Product> productFound = this.productRepository.findByName(name);
        if(productFound.isPresent()){
            throw new ProductBadRequestException("Product with name %s already exists".formatted(name));
        }
    }
}
