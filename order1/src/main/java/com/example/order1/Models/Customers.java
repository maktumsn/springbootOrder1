package com.example.order1.Models;

import org.springframework.stereotype.Component;

@Component
public class Customers {
    private long customerid;
    private String name;
    private String email;
    private long phoneno;

    public Customers() {

    }

    public Customers(long customerid, String name, String email, long phoneno) {
        this.customerid = customerid;
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
    }

    public long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(long customerid) {
        this.customerid = customerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(long phoneno) {
        this.phoneno = phoneno;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "customerid=" + customerid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneno=" + phoneno +
                '}';
    }
}
