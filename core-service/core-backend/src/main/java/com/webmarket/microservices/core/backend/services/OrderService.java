package com.webmarket.microservices.core.backend.services;

import com.webmarket.microservices.api.exceptions.ResourceNotFoundException;
import com.webmarket.microservices.cart.api.CartDto;
import com.webmarket.microservices.core.api.OrderDetailsDto;
import com.webmarket.microservices.core.backend.entities.Order;
import com.webmarket.microservices.core.backend.entities.OrderItem;
import com.webmarket.microservices.core.backend.integrations.CartServiceIntegration;
import com.webmarket.microservices.core.backend.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;

    private final ProductService productService;
    private final CartServiceIntegration cartServiceIntegration;


    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto){
        Order order = new Order();
        CartDto cartDto = cartServiceIntegration.getUserCart(username);
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(cartDto.getTotalPrice());
        List<OrderItem> items = cartDto.getItems().stream()
                .map(orderItemDto ->{
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setQuantity(orderItemDto.getQuantity());
                    orderItem.setPricePerProduct(orderItemDto.getPricePerProduct());
                    orderItem.setPrice(orderItemDto.getPrice());
                    orderItem.setProduct(productService.findById(orderItemDto.getProductId()).orElseThrow(() ->new ResourceNotFoundException("Product not found")));
                    return orderItem;
                }).collect(Collectors.toList());
        order.setItems(items);
        ordersRepository.save(order);
        cartServiceIntegration.clearUserCart(username);
    }





    public List<Order> findOrdersByUsername(String username){/**Метод сервиса не должен возвращать dto, т.к. его может использовать другой сервис и он получит в ответ dto. Сервер должен работать с сущностью */
        return ordersRepository.findAllByUsername(username);
    }



}
