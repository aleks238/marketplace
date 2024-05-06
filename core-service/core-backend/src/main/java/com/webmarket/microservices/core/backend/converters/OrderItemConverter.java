package com.webmarket.microservices.core.backend.converters;

import com.webmarket.microservices.core.api.OrderItemDto;
import com.webmarket.microservices.core.backend.entities.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {

    public OrderItemDto entityToDto(OrderItem orderItem){
        return new OrderItemDto(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getTitle(),
                orderItem.getQuantity(),
                orderItem.getPricePerProduct(),
                orderItem.getPrice()
                );
    }
/*
    public OrderItem dtoToEntity(OrderItemDto orderItemDto){
        return new OrderItem();
    }

 */
}
