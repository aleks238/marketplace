package com.webmarket.microservices.core.api;


import java.util.List;

public class OrderDto {
    private Long id;
    private String userName;
    private List<OrderItemDto> items;
    private Integer totalPrice;
    private String address;
    private String phone;

    public OrderDto() {
    }

    public OrderDto(Long id, String userName, List<OrderItemDto> items, Integer totalPrice, String address, String phone) {
        this.id = id;
        this.userName = userName;
        this.items = items;
        this.totalPrice = totalPrice;
        this.address = address;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
