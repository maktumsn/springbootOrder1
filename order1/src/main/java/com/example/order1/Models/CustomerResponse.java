package com.example.order1.Models;

import java.util.List;

public class CustomerResponse {
    List<Customers> Customers;

    public CustomerResponse() {
    }

    public CustomerResponse(List<com.example.order1.Models.Customers> customers) {
        Customers = customers;
    }

    public List<com.example.order1.Models.Customers> getCustomers() {
        return Customers;
    }

    public void setCustomers(List<com.example.order1.Models.Customers> customers) {
        Customers = customers;
    }
}

