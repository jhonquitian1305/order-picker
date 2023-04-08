package com.orderpicker.product.infrastructure.controller;

import com.orderpicker.product.application.exception.ProductBadRequestException;
import com.orderpicker.product.application.mapper.MapperProduct;
import com.orderpicker.product.infrastructure.dto.ProductDTO;
import com.orderpicker.product.infrastructure.response.ProductResponse;
import com.orderpicker.product.infrastructure.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import static com.orderpicker.product.infrastructure.constants.ProductPaginationRequest.*;

@RestController
@RequestMapping(ENDPOINT_PRODUCTS)
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class ProductController {

    private final @NonNull ProductService productService;

    private final @NonNull MapperProduct mapperProduct;

    @Operation(summary = "Create a Product")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product Created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid values entry",
                    content = @Content
            )
    })
    @PostMapping
    ResponseEntity<ProductDTO> saveOne(
            @Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            throw new ProductBadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return new ResponseEntity<>(this.mapperProduct.mapProductDTO(this.productService.saveOne(productDTO)), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a Product by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product Found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to apply",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product Not Found",
                    content = @Content
            )
    })
    @GetMapping(ENDPOINT_PRODUCT_ID)
    ResponseEntity<ProductDTO> getById(
            @Parameter(description = "Product ID to search")
            @PathVariable("id") Long id
    ){
        return new ResponseEntity<>(this.mapperProduct.mapProductDTO(this.productService.getById(id)), HttpStatus.OK);
    }

    @Operation(summary = "Get All Products")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get All Products",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to apply",
                    content = @Content
            )
    }
    )
    @GetMapping
    ResponseEntity<ProductResponse> getAll(
            @Parameter(description = "Choose a page number in the search")
            @RequestParam(value = "pageNumber", defaultValue = PRODUCT_DEFAULT_NUMBER_PAGE, required = false) int pageNumber,
            @Parameter(description = "Choose a page size in the search")
            @RequestParam(value = "pageSize", defaultValue = PRODUCT_DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @Parameter(description = "Sort the answer by a field")
            @RequestParam(value = "sortBy", defaultValue = PRODUCT_DEFAULT_SORT_BY, required = false) String sortBy,
            @Parameter(description = "Sort the answer by a direction")
            @RequestParam(value = "sortDir", defaultValue = PRODUCT_DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        return new ResponseEntity<>(this.productService.getAll(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @Operation(summary = "Get a Product by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product Found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to apply",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product Not Found",
                    content = @Content
            )
    })
    @GetMapping(ENDPOINT_PRODUCT_NAME)
    ResponseEntity<ProductDTO> getByName(
            @Parameter(description = "Name product to search")
            @PathVariable("name") String name
    ){
        return new ResponseEntity<>(this.mapperProduct.mapProductDTO(this.productService.getByName(name)), HttpStatus.OK);
    }

    @Operation(summary = "Update a Product by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product Updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to apply",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product Not Found",
                    content = @Content
            )
    })
    @PutMapping(ENDPOINT_PRODUCT_ID)
    ResponseEntity<ProductDTO> updateOneById(
            @Parameter(description = "Product ID to be updated")
            @PathVariable("id") Long id,
            @Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            throw new ProductBadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return new ResponseEntity<>(this.mapperProduct.mapProductDTO(this.productService.updateOneById(id, productDTO)), HttpStatus.OK);
    }

    @Operation(summary = "Update price of a Product by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product's price Updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to apply",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product Not Found",
                    content = @Content
            )
    })
    @PatchMapping(ENDPOINT_PRODUCT_PRICE)
    ResponseEntity<ProductDTO> updatePrice(
            @Parameter(description = "Product ID to be updated")
            @PathVariable("id") Long id,
            @RequestBody Double price
    ){
        return new ResponseEntity<>(this.mapperProduct.mapProductDTO(this.productService.updatePrice(id, price)), HttpStatus.OK);
    }

    @Operation(summary = "Update amount of a Product by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product's amount Updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to apply",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product Not Found",
                    content = @Content
            )
    })
    @PatchMapping(ENDPOINT_PRODUCT_AMOUNT)
    ResponseEntity<ProductDTO> registerProductEntry(
            @Parameter(description = "Product name to be updated")
            @PathVariable("name") String name,
            @RequestBody int amount
    ){
        return new ResponseEntity<>(this.mapperProduct.mapProductDTO(this.productService.registerProductEntry(name, amount)), HttpStatus.OK);
    }

    @Operation(summary = "Delete a Product by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product Deleted",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to apply",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product Not Found",
                    content = @Content
            )
    })
    @DeleteMapping(ENDPOINT_PRODUCT_ID)
    ResponseEntity<String> deleteOneById(
            @Parameter(description = "Product ID to be Deleted")
            @PathVariable("id") Long id
    ){
        this.productService.deleteOneById(id);
        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }
}
