package com.webmarket.microservices.cart.backend.converters;

import com.webmarket.microservices.cart.api.CartDto;
import com.webmarket.microservices.cart.api.CartItemDto;
import com.webmarket.microservices.cart.backend.model.Cart;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartConverter {
    public CartDto modelToDto(Cart cart){
        List<CartItemDto> cartItemDtos = cart.getItems().stream().map(cartItem -> new CartItemDto(
                cartItem.getProductId(),
                cartItem.getProductTitle(),
                cartItem.getQuantity(),
                cartItem.getPricePerProduct(),
                cartItem.getPrice())).collect(Collectors.toList()
        );
        CartDto cartDto = new CartDto(cartItemDtos, cart.getTotalPrice());
        return cartDto;
    }
}
