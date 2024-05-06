package com.webmarket.microservices.core.backend.controllers;

import com.webmarket.microservices.core.api.OrderDetailsDto;
import com.webmarket.microservices.core.api.OrderDto;
import com.webmarket.microservices.core.backend.converters.OrderConverter;
import com.webmarket.microservices.core.backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrdersController {
    private final OrderService orderService;

    private final OrderConverter orderConverter;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto){//В хедере подшито username, берем оттуда
    orderService.createOrder(username,orderDetailsDto);
    }

    @GetMapping
    public List<OrderDto> getUserOrders(@RequestHeader String username){
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

/*

    @GetMapping()
    public OrderDto createOrder(
            @RequestParam (name = "address") String address,
            @RequestParam (name = "phone") String phone,
            @RequestParam (name = "username") String username
    ){
       OrderDto orderDto = orderService.createOrder(address, phone, username);
       log.info("ORDER #" + orderDto.getId() + " " + orderDto.toString());
       return orderDto;
       //return orderService.createOrder(address, phone, username);
    }

 */
}
