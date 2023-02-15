package com.orderpicker.product.application.usecase;

import com.orderpicker.product.application.exception.ProductBadRequestException;
import com.orderpicker.product.application.exception.ProductNotFoundException;
import com.orderpicker.product.application.mapper.MapperProduct;
import com.orderpicker.product.domain.model.Product;
import com.orderpicker.product.domain.repository.ProductRepository;
import com.orderpicker.product.infrastructure.dto.ProductDTO;
import com.orderpicker.product.infrastructure.response.ProductResponse;
import com.orderpicker.product.infrastructure.service.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public ProductResponse getAll(int numberPage, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(numberPage, pageSize, sort);

        Page<Product> productsFound = this.productRepository.findAll(pageable);

        List<ProductDTO> products = productsFound.getContent().stream().map(this.mapperProduct::mapProductDTO).toList();

        return ProductResponse.builder()
                .content(products)
                .pageNumber(productsFound.getNumber())
                .pageSize(productsFound.getSize())
                .totalElements(productsFound.getTotalElements())
                .totalPages(productsFound.getTotalPages())
                .lastOne(productsFound.isLast())
                .build();
    }

    @Override
    public Product getByName(String name) {
        Optional<Product> productFound = this.productRepository.findByName(name);
        if(productFound.isEmpty()){
            throw new ProductNotFoundException("Product with name %s doesn't exist".formatted(name));
        }
        return productFound.get();
    }

    @Override
    public Product updateOneById(Long id, ProductDTO productDTO) {
        Product productFound = this.getById(id);

        Product productUpdated = this.mapperProduct.updateProduct(productFound, productDTO);

        return this.productRepository.save(productUpdated);
    }

    @Override
    public Product updatePrice(Long id, Double price) {
        Product productFound = this.getById(id);

        productFound.setPrice(price);

        return this.productRepository.save(productFound);
    }

    @Override
    public Product registerProductEntry(String name, int amount) {
        Product productFound = this.getByName(name);

        productFound.setAmount(productFound.getAmount() + amount);

        return this.productRepository.save(productFound);
    }

    @Override
    public Product registerProductOut(Long id, int amount) {
        Product productFound = this.getById(id);

        productFound.setAmount(productFound.getAmount() - amount);

        return this.productRepository.save(productFound);
    }

    @Override
    public Double getTotalPriceProduct(Product product, int amount) {
        return product.getPrice() * amount;
    }

    @Override
    public void deleteOneById(Long id) {
        this.getById(id);

        this.productRepository.deleteById(id);
    }

    protected void findByName(String name){
        Optional<Product> productFound = this.productRepository.findByName(name);
        if(productFound.isPresent()){
            throw new ProductBadRequestException("Product with name %s already exists".formatted(name));
        }
    }
}
