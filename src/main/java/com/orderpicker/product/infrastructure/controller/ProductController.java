package com.orderpicker.product.infrastructure.controller;

import com.orderpicker.product.application.exception.ProductBadRequestException;
import com.orderpicker.product.application.mapper.MapperProduct;
import com.orderpicker.product.infrastructure.dto.ProductDTO;
import com.orderpicker.product.infrastructure.service.ProductService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.orderpicker.product.infrastructure.constants.ProductEndpointsConstants.ENDPOINT_PRODUCTS;
import static com.orderpicker.product.infrastructure.constants.ProductEndpointsConstants.ENDPOINT_PRODUCT_ID;

@RestController
@RequestMapping(ENDPOINT_PRODUCTS)
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class ProductController {

    private final @NonNull ProductService productService;

    private final @NonNull MapperProduct mapperProduct;

    @PostMapping
    ResponseEntity<ProductDTO> saveOne(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ProductBadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return new ResponseEntity<>(this.mapperProduct.mapProductDTO(this.productService.saveOne(productDTO)), HttpStatus.CREATED);
    }

    @GetMapping(ENDPOINT_PRODUCT_ID)
    ResponseEntity<ProductDTO> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(this.mapperProduct.mapProductDTO(this.productService.getById(id)), HttpStatus.OK);
    }
}
