package com.webmarket.microservices.cart.backend.services;

import com.webmarket.microservices.api.exceptions.ResourceNotFoundException;
import com.webmarket.microservices.cart.backend.integrations.CoreServiceIntegration;
import com.webmarket.microservices.cart.backend.model.Cart;
import com.webmarket.microservices.core.api.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CoreServiceIntegration coreServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }


    public void addToCart(String cartKey, Long productId) {
        ProductDto productDto = coreServiceIntegration.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт с id:" + productId + " не найден"));
        execute(cartKey, c -> {
            c.add(productDto);
        });
    }

    public void addToCart(String cartKey, ProductDto productDto) {
        execute(cartKey, c -> {
            c.add(productDto);
        });
    }

    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, cart -> cart.remove(productId));
    }

    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, cart -> cart.decrement(productId));
    }

    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCart(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void copyGuestCartToUserCart(String userCartKey, String guestCartKey) {
        Cart guestCart = getCart(guestCartKey);
        Cart userCart = getCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}
