package com.example.order1.Controller;

import java.util.ArrayList;
import java.util.List;


import com.example.order1.Models.Orders;
import com.example.order1.Models.OrdersResponse;
import com.example.order1.Services.OrdersService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class OrdersController {


    private OrdersService ordersService ;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/orders")
    public ResponseEntity<?> AllOrders(@RequestParam(defaultValue ="0") String customerid,
                                             @RequestParam(defaultValue ="0")String ordernumber)
    {
        List<Orders> orders = null;
        if (ordernumber.equals("0"))
        {
            if (customerid.equals("0"))
            {
                orders = ordersService.retrieveAllOrders();
            }
            else
            {
                orders = ordersService.retrieveAllOrders(Long.parseLong(customerid));
            }


        } else {

            orders = ordersService.retrieveOneOrder(Integer.parseInt(ordernumber));

        }

        return ResponseEntity.ok().body(new OrdersResponse(orders));
    }

    @PostMapping("/customers/customerid")
    public ResponseEntity<?> CreateOrder( @RequestBody Orders orders,@RequestParam long  customerid ) {
        Orders savedOrder = ordersService.addOrders(orders);
        return ResponseEntity.accepted().body(savedOrder);

    }

    @DeleteMapping("/customers/ordernumber")
    public void removeOrder(@RequestParam int ordernumber) {
        List<?> orders = ordersService.retrieveOneOrder(ordernumber);

        ordersService.deleteOrder(ordernumber);
    }

    @CachePut(cacheNames = "orders")
    @PutMapping("/customers/customerid/ordernumber")
    public ResponseEntity<?> updateOrder(@RequestBody Orders orders, @RequestParam int customerid,@RequestParam int ordernumber) {
        Orders order=  ordersService.updateOrder(orders);
        return ResponseEntity.accepted().body(order);

    }

}

