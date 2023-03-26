package com.orderpicker.order.infrastructure.controller;

import com.orderpicker.order.application.exception.OrderBadRequestException;
import com.orderpicker.order.application.mapper.MapperOrder;
import com.orderpicker.order.infrastructure.dto.OrderDTO;
import com.orderpicker.order.infrastructure.dto.OrderInformation;
import com.orderpicker.order.infrastructure.dto.Orders;
import com.orderpicker.order.infrastructure.response.OrderDTOResponse;
import com.orderpicker.order.infrastructure.response.OrderUserResponse;
import com.orderpicker.order.infrastructure.response.OrdersResponse;
import com.orderpicker.order.infrastructure.service.OrderService;
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

    @PostMapping(ENDPOINT_ORDER_USER)
    ResponseEntity<OrderDTOResponse> saveOne(
            @PathVariable("idUser") Long idUser,
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult bindingResult
    ){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.orderService.validateUserRequestById(idUser, userEmail);
        if(bindingResult.hasErrors()){
            throw new OrderBadRequestException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return new ResponseEntity<>(this.mapperOrder.mapOrderDTOResponse(this.orderService.createOrder(idUser, orderDTO)), HttpStatus.CREATED);
    }

    @GetMapping(ENDPOINT_ORDER_USER)
    ResponseEntity<OrderUserResponse> getAllByClient(
            @PathVariable("idUser") Long idUser,
            @RequestParam(value = "pageNumber", defaultValue = ORDER_DEFAULT_NUMBER_PAGE, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = ORDER_DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = ORDER_DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = ORDER_DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.orderService.validateUserRequestById(idUser, userEmail);
        return new ResponseEntity<>(this.orderService.getAllByClient(idUser, pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<OrdersResponse> getAll(
            @RequestParam(value = "delivered", defaultValue = ORDER_DEFAULT_DELIVERED, required = false) String delivered,
            @RequestParam(value = "pageNumber", defaultValue = ORDER_DEFAULT_NUMBER_PAGE, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = ORDER_DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = ORDER_DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = ORDER_DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        this.orderService.validateRole(userEmail);
        return new ResponseEntity<>(this.orderService.getAll(delivered, pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_ORDER_ID)
    ResponseEntity<Orders> getOneById(@PathVariable("id") Long id){
        return new ResponseEntity<>(this.orderService.getOneById(id), HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_ORDER_USER_ID)
    ResponseEntity<OrderInformation> getOneByIdAndUser(
            @PathVariable("idUser") Long idUser,
            @PathVariable("id") Long id
    ){
        return new ResponseEntity<>(this.orderService.getOneByIdAndUser(idUser, id), HttpStatus.OK);
    }
}
