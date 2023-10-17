package com.orderpicker.order.infrastructure.controller;

import com.orderpicker.exception.BadRequestException;
import com.orderpicker.order.application.mapper.MapperOrder;
import com.orderpicker.order.infrastructure.dto.OrderDTO;
import com.orderpicker.order.infrastructure.dto.OrderInformation;
import com.orderpicker.order.infrastructure.dto.Orders;
import com.orderpicker.order.infrastructure.response.OrderDTOResponse;
import com.orderpicker.order.infrastructure.response.OrderUserResponse;
import com.orderpicker.order.infrastructure.response.OrdersResponse;
import com.orderpicker.order.infrastructure.service.OrderService;
import com.orderpicker.orderdetail.infrastructure.dto.OrderDetailDTO;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.orderpicker.order.infrastructure.constants.OrderEndpointsConstants.*;
import static com.orderpicker.order.infrastructure.constants.OrderPaginationRequest.*;

@RestController
@RequestMapping(ENDPOINT_ORDERS)
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class OrderController {

    private final @NonNull OrderService orderService;

    private final @NonNull MapperOrder mapperOrder;

    @Operation(summary = "Create an Order")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Order Created",
                    headers = {
                            @Header(name = "Authorization", description = "Token authorization")
                    },
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDTOResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid values entry",
                    content = @Content
            )
    })
    @PostMapping(ENDPOINT_ORDER_USER)
    ResponseEntity<OrderDTOResponse> saveOne(
            @Parameter(description = "User ID that do the order")
            @PathVariable("idUser") Long idUser,
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult bindingResult
    ){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.orderService.validateUserRequestById(idUser, userEmail);
        if(bindingResult.hasErrors()){
            throw new BadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return new ResponseEntity<>(this.mapperOrder.mapOrderDTOResponse(this.orderService.createOrder(idUser, orderDTO)), HttpStatus.CREATED);
    }

    @Operation(summary = "Get All Orders by a client")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All orders obtained of a client",
                    headers = {
                            @Header(name = "Authorization", description = "Token authorization")
                    },
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderUserResponse.class)
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
                    description = "User Not Found",
                    content = @Content
            )
    })
    @GetMapping(ENDPOINT_ORDER_USER)
    ResponseEntity<OrderUserResponse> getAllByClient(
            @Parameter(description = "User ID to get your orders")
            @PathVariable("idUser") Long idUser,
            @Parameter(description = "Choose a page number in the search")
            @RequestParam(value = "pageNumber", defaultValue = ORDER_DEFAULT_NUMBER_PAGE, required = false) int pageNumber,
            @Parameter(description = "Choose a page size in the search")
            @RequestParam(value = "pageSize", defaultValue = ORDER_DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @Parameter(description = "Sort the answer by a field")
            @RequestParam(value = "sortBy", defaultValue = ORDER_DEFAULT_SORT_BY, required = false) String sortBy,
            @Parameter(description = "Sort the answer by a direction")
            @RequestParam(value = "sortDir", defaultValue = ORDER_DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.orderService.validateUserRequestById(idUser, userEmail);
        return new ResponseEntity<>(this.orderService.getAllByClient(idUser, pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @Operation(summary = "Get All Orders")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All Orders obtained",
                    headers = {
                            @Header(name = "Authorization", description = "Token authorization")
                    },
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrdersResponse.class)
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
    ResponseEntity<OrdersResponse> getAll(
            @Parameter(description = "Condition to get orders only delivered or not delivered")
            @RequestParam(value = "delivered", defaultValue = ORDER_DEFAULT_DELIVERED, required = false) String delivered,
            @Parameter(description = "Choose a page number in the search")
            @RequestParam(value = "pageNumber", defaultValue = ORDER_DEFAULT_NUMBER_PAGE, required = false) int pageNumber,
            @Parameter(description = "Choose a page size in the search")
            @RequestParam(value = "pageSize", defaultValue = ORDER_DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @Parameter(description = "Sort the answer by a field")
            @RequestParam(value = "sortBy", defaultValue = ORDER_DEFAULT_SORT_BY, required = false) String sortBy,
            @Parameter(description = "Sort the answer by a direction")
            @RequestParam(value = "sortDir", defaultValue = ORDER_DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.orderService.validateRole(userEmail);
        return new ResponseEntity<>(this.orderService.getAll(delivered, pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @Operation(summary = "Get an Order by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order Found",
                    headers = {
                            @Header(name = "Authorization", description = "Token authorization")
                    },
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Orders.class)
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
                    description = "Order Not Found",
                    content = @Content
            )
    })
    @GetMapping(ENDPOINT_ORDER_ID)
    ResponseEntity<Orders> getOneById(
            @Parameter(description = "Order ID to search")
            @PathVariable("id") Long id
    ){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.orderService.validateRole(userEmail);
        return new ResponseEntity<>(this.orderService.getOneById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get an Order by user and id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order Found",
                    headers = {
                            @Header(name = "Authorization", description = "Token authorization")
                    },
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrderInformation.class)
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
                    description = "User Not Found or Order Not Found or Order Not Belong to User",
                    content = @Content
            )
    })
    @GetMapping("/order-details/{idUser}/{idOrder}")
    ResponseEntity<OrderDetailDTO> getOrderDetailsById(
            @Parameter(description = "User ID to search your order")
            @PathVariable Long idUser,
            @Parameter(description = "Order ID to search")
            @PathVariable Long idOrder
    ){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.orderService.validateUserRequestById(idUser, userEmail);
        return new ResponseEntity<>(this.orderService.getOneDetailsById(idUser, idOrder), HttpStatus.OK);
    }
}
