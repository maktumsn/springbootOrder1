package com.example.order1.Models;

public class Data {
    CustomerResponse data;

    public Data() {
    }

    public Data(CustomerResponse data) {
        this.data = data;
    }

    public CustomerResponse getData() {
        return data;
    }

    public void setData(CustomerResponse data) {
        this.data = data;
    }
}
