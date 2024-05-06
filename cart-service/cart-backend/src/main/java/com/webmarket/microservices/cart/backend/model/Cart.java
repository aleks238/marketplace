package com.webmarket.microservices.cart.backend.model;

import com.webmarket.microservices.core.api.ProductDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Cart {

    private List<CartItem> items;
    private int totalPrice;
    public Cart() {
        this.items = new ArrayList<>();
    }

    public void add(ProductDto productDto){
        if (add(productDto.getId())){
            return;
        }
        items.add(new CartItem(productDto));
        recalculate();
    }

    public boolean add(Long id){
        for (CartItem cartItem : items){
            if (cartItem.getProductId().equals(id)){
                cartItem.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void remove(Long productID){
        items.removeIf(cartItem -> cartItem.getProductId().equals(productID));
        recalculate();
    }

    public void decrement(Long productId){
        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()){
            CartItem cartItem = iterator.next();
            if (cartItem.getProductId().equals(productId)){
                cartItem.changeQuantity(-1);
                if (cartItem.getQuantity() <= 0){
                    iterator.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void clear(){
        items.clear();
        totalPrice = 0;
    }

    public void merge(Cart guestCart){
        for (CartItem guestCartCartItem : guestCart.items){
            boolean merged = false;
            for (CartItem userCartCartItem : items){
                if(userCartCartItem.getProductId().equals(guestCartCartItem.getProductId())){
                    userCartCartItem.changeQuantity(guestCartCartItem.getQuantity());
                    merged = true;
                    break;
                }
            }
            if (!merged){
                items.add(guestCartCartItem);
            }
        }
        recalculate();
        guestCart.clear();
    }


    private void recalculate(){
        totalPrice = 0;
        for(CartItem cartItem : items){
            totalPrice += cartItem.getPrice();
        }
    }


}
