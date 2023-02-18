package com.orderpicker.order.infrastructure.controller;

import com.orderpicker.order.application.mapper.MapperOrder;
import com.orderpicker.order.infrastructure.dto.OrderDTO;
import com.orderpicker.order.infrastructure.response.OrderDTOResponse;
import com.orderpicker.order.infrastructure.response.OrderUserResponse;
import com.orderpicker.order.infrastructure.service.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.orderpicker.order.infrastructure.constants.OrderEndpointsConstants.ENDPOINT_ORDERS;
import static com.orderpicker.order.infrastructure.constants.OrderEndpointsConstants.ENDPOINT_ORDER_USER;
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
            @RequestBody OrderDTO orderDTO
    ){
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
        return new ResponseEntity<>(this.orderService.getAllByClient(idUser, pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }
}
