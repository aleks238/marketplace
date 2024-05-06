package com.webmarket.microservices.core.backend.converters;

import com.webmarket.microservices.core.api.OrderDto;
import com.webmarket.microservices.core.backend.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public OrderDto entityToDto(Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setAddress(order.getAddress());
        orderDto.setPhone(order.getPhone());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setUserName(order.getUsername());
        orderDto.setItems(order.getItems().stream().map(orderItemConverter::entityToDto).collect(Collectors.toList()));
        return orderDto;
    }

/*
    public Order dtoToEntity(OrderDto orderDto){
        return ;
    }
 */
}
