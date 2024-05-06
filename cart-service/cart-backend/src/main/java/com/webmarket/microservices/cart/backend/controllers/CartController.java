package com.webmarket.microservices.cart.backend.controllers;

import com.webmarket.microservices.api.dto.StringResponse;
import com.webmarket.microservices.cart.api.CartDto;
import com.webmarket.microservices.cart.backend.converters.CartConverter;
import com.webmarket.microservices.cart.backend.model.Cart;
import com.webmarket.microservices.cart.backend.services.CartService;
import com.webmarket.microservices.core.api.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController()
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    private final RestTemplate restTemplate;
    private final CartConverter cartConverter;

    @GetMapping("/{uuid}")
    public CartDto getCurrentCurt(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        return cartConverter.modelToDto(cartService.getCart(getCurrentCartUuid(username, uuid)));
    }

    @GetMapping("/generate")
    public StringResponse getCart(){
        return new StringResponse(cartService.generateCartUuid());
    }




    @GetMapping("/{uuid}/add/{productId}")
    public void add(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId){
        //cartService.addToCart(getCurrentCartUuid(username, uuid), productId);
        cartService.addToCart(
                getCurrentCartUuid(username, uuid),

                restTemplate.getForObject("http://localhost:5555/core/api/v1/products/" + productId, ProductDto.class)
        );
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrement(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId){
        cartService.decrementItem(getCurrentCartUuid(username,uuid),productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void remove(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId){
        cartService.removeItemFromCart(getCurrentCartUuid(username,uuid),productId);
    }

    @GetMapping("/{uuid}/clear")
    public void clear(@RequestHeader(required = false) String username, @PathVariable String uuid){
        cartService.clearCart(getCurrentCartUuid(username,uuid));
    }

    @GetMapping("/{uuid}/merge")
    public void copyGuestCartToUserCart(@RequestHeader String username, @PathVariable String uuid){
        cartService.copyGuestCartToUserCart(
                getCurrentCartUuid(username,null),
                getCurrentCartUuid(null, uuid)
               );
    }

    private String getCurrentCartUuid(String username, String uuid) {
        if (username != null) {
            return cartService.getCartUuidFromSuffix(username);
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }

    @GetMapping("/getCurtByUsername")
    private Cart getCurrentCartByUsername(String username) {
        String uuid = cartService.getCartUuidFromSuffix(username);
        return cartService.getCart(getCurrentCartUuid(username, uuid));



    }

}
