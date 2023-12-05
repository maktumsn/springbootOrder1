package com.example.order1.Repository;


import com.example.order1.Models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrdersRepository extends JpaRepository<Orders,Integer> {


    List<Orders> findBycustomerid(long customerid);
}
