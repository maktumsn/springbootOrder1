package com.example.order1.Models;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("data")
public class OrdersResponse {
    List<Orders> Orders;

    public OrdersResponse() {
    }

    public OrdersResponse(List<Orders> orders) {
        Orders = orders;
    }

    public List<Orders> getOrders() {
        return Orders;
    }

    public void setOrders(List<Orders> orders) {
        Orders = orders;
    }
}