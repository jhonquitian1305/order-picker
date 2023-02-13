package com.orderpicker.product.infrastructure.controller;

import com.orderpicker.product.application.exception.ProductBadRequestException;
import com.orderpicker.product.application.mapper.MapperProduct;
import com.orderpicker.product.infrastructure.dto.ProductDTO;
import com.orderpicker.product.infrastructure.response.ProductResponse;
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

import static com.orderpicker.product.infrastructure.constants.ProductEndpointsConstants.*;
import static com.orderpicker.user.infrastructure.constants.UserPaginationRequest.*;
import static com.orderpicker.user.infrastructure.constants.UserPaginationRequest.USER_DEFAULT_SORT_DIR;

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

    @GetMapping
    ResponseEntity<ProductResponse> getAll(
            @RequestParam(value = "pageNumber", defaultValue = USER_DEFAULT_NUMBER_PAGE, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = USER_DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = USER_DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = USER_DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        return new ResponseEntity<>(this.productService.getAll(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_PRODUCT_NAME)
    ResponseEntity<ProductDTO> getByName(@PathVariable("name") String name){
        return new ResponseEntity<>(this.mapperProduct.mapProductDTO(this.productService.getByName(name)), HttpStatus.OK);
    }

    @PutMapping(ENDPOINT_PRODUCT_ID)
    ResponseEntity<ProductDTO> updateOneById(@PathVariable("id") Long id, @Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ProductBadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return new ResponseEntity<>(this.mapperProduct.mapProductDTO(this.productService.updateOneById(id, productDTO)), HttpStatus.OK);
    }

    @PutMapping(ENDPOINT_PRODUCT_PRICE)
    ResponseEntity<ProductDTO> updatePrice(@PathVariable("id") Long id, @RequestBody Double price){
        return new ResponseEntity<>(this.mapperProduct.mapProductDTO(this.productService.updatePrice(id, price)), HttpStatus.OK);
    }
}
