package com.orderpicker.delivery.infrastructure.controller;

import com.orderpicker.delivery.application.mapper.MapperDelivery;
import com.orderpicker.delivery.infrastructure.dto.DeliveryDTO;
import com.orderpicker.delivery.infrastructure.dto.DeliveryInformation;
import com.orderpicker.delivery.infrastructure.response.DeliveryDTOResponse;
import com.orderpicker.delivery.infrastructure.response.DeliveryResponse;
import com.orderpicker.delivery.infrastructure.service.DeliveryService;
import com.orderpicker.exception.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
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

import static com.orderpicker.delivery.infrastructure.constants.DeliveryEndpointsConstants.*;
import static com.orderpicker.delivery.infrastructure.constants.DeliveryPaginationRequest.*;

@RestController
@RequestMapping(ENDPOINT_DELIVERIES)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryController {

    private final @NonNull DeliveryService deliveryService;

    private final @NonNull MapperDelivery mapperDelivery;

    @Operation(summary = "Create a Delivery")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Delivery Created",
                    headers = {
                            @Header(name = "Authorization", description = "Token authorization")
                    },
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeliveryDTOResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid values entry",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order Not Found",
                    content = @Content
            )
    })
    @PostMapping
    ResponseEntity<DeliveryDTOResponse> createOne(
            @Valid @RequestBody DeliveryDTO deliveryDTO,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return new ResponseEntity<>(this.mapperDelivery.mapDeliveryDTOResponse(this.deliveryService.createOne(deliveryDTO)), HttpStatus.CREATED);
    }

    @Operation(summary = "Get All Deliveries")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All Deliveries obtained",
                    headers = {
                            @Header(name = "Authorization", description = "Token authorization")
                    },
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeliveryResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No permission to apply",
                    content = @Content
            )
    })
    @GetMapping
    ResponseEntity<DeliveryResponse> getAllDeliveries(
            @Parameter(description = "Field to do a search by user")
            @RequestParam(value = "model", defaultValue = DELIVERY_DEFAULT_MODEL, required = false) String model,
            @Parameter(description = "User ID to search")
            @RequestParam(value = "id", defaultValue = DELIVERY_DEFAULT_ID, required = false) String id,
            @Parameter(description = "Choose a page number in the search")
            @RequestParam(value = "pageNumber", defaultValue = DELIVERY_DEFAULT_NUMBER_PAGE, required = false) int pageNumber,
            @Parameter(description = "Choose a page size in the search")
            @RequestParam(value = "pageSize", defaultValue = DELIVERY_DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @Parameter(description = "Sort the answer by a field")
            @RequestParam(value = "sortBy", defaultValue = DELIVERY_DEFAULT_SORT_BY, required = false) String sortBy,
            @Parameter(description = "Sort the answer by a direction")
            @RequestParam(value = "sortDir", defaultValue = DELIVERY_DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        return new ResponseEntity<>(this.deliveryService.getAllDeliveries(model, id, pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @Operation(summary = "Get a Delivery by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Delivery Found",
                    headers = {
                            @Header(name = "Authorization", description = "Token authorization")
                    },
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeliveryInformation.class)
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
                    description = "Delivery Not Found",
                    content = @Content
            )
    })
    @GetMapping(ENDPOINT_DELIVERIES_ID)
    ResponseEntity<DeliveryInformation> getOneById(
            @Parameter(description = "Delivery ID to search")
            @PathVariable("id") Long id
    ){
        return new ResponseEntity<>(this.deliveryService.getOneById(id), HttpStatus.OK);
    }

    @Operation(summary = "Mark a Delivery as delivered")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Delivery Marked as delivered",
                    headers = {
                            @Header(name = "Authorization", description = "Token authorization")
                    },
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
                    description = "Delivery Not Found",
                    content = @Content
            )
    })
    @PatchMapping(ENDPOINT_DELIVERIES_DELIVERED)
    ResponseEntity<String> orderDelivered(
            @Parameter(description = "Delivery ID to mark as delivered")
            @PathVariable("id") Long id
    ){
        this.deliveryService.orderDelivered(id);
        return new ResponseEntity<>("Delivery has been delivered", HttpStatus.OK);
    }
}
