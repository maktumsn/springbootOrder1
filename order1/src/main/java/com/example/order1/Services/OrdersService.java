package com.example.order1.Services;

import com.example.order1.Exceptions.OrderNotFoundException;
import com.example.order1.Models.CustomerResponse;
import com.example.order1.Models.Customers;
import com.example.order1.Models.Data;
import com.example.order1.Models.Orders;
import com.example.order1.Repository.OrdersRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

;

@Service
public class OrdersService {


    private OrdersRepository repository;
    private Customers customer;

    public OrdersService(OrdersRepository repository, Customers customer) {
        this.repository = repository;
        this.customer = customer;
    }

    @Cacheable(cacheNames = "orders")
    public List<Orders> retrieveAllOrders() {
        return repository.findAll();
    }

    public List<Orders> retrieveAllOrders(long customer_id) {
        RestTemplate restTemplate =new RestTemplate();
        Customers[] l= restTemplate.getForObject("http://localhost:8100/customers", Customers[].class);
        for(Customers i:l)
        {
            if(customer_id==i.getCustomerid())
            {
                List<Orders> savedCustomerOrder = repository.findBycustomerid(customer_id);
                return savedCustomerOrder;
            }

        }
        return null;
    }

    @Cacheable(cacheNames = "orders")
    public List<Orders> retrieveOneOrder(int ordernumber) {
        List<Orders> orders=new ArrayList<>();
        Orders order= repository.findById(ordernumber).orElse(null);

        if(order == null){
            throw new OrderNotFoundException("Order with ordernumber "+ordernumber+ " is not present." );
        }
        orders.add(order);
        return orders;
    }

    public Orders addOrders(Orders orders)
    {
//        if (orders.getPickupdate()==null || orders.getPickupdate()==null)
//            throw new InvalidOrderFormat("Pickup time and date are mandatory fields.");
//        RestTemplate restTemplate =new RestTemplate();
//        Customer[] l= restTemplate.getForObject("http://localhost:8100/customers",Customer[].class);
//        for(Customer i:l)
//        {
//            if(orders.getCustomerid()==i.getCustomerid())
//            {
//                Orders savedCustomerOrder = repository.save(orders);
//                return savedCustomerOrder;
//            }
//
//        }
//        throw new CustomerNotFoundException("Customer Not Found");
        RestTemplate restTemplate =new RestTemplate();
        String url= "http://localhost:8100/customers";
        RequestCallback requestCallback = request -> request.getHeaders().
                setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
        ResponseExtractor<Void> responseExtractor = response -> {
            Path path = Paths.get("storing.json");
            Files.copy(response.getBody(), path, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(response.getClass());
            return null;
        };
        restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor);
        ObjectMapper objectMapper = new ObjectMapper();
      //  objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        try {

            Data data = objectMapper.readValue(new File("storing.json"),
                    new TypeReference<Data>() {});
            CustomerResponse customerResponse=data.getData();
            List<Customers> customers= customerResponse.getCustomers();

            for (Customers customer : customers) {
                if (orders.getCustomerid() == customer.getCustomerid()) {
                    Orders savedCustomerOrder = repository.save(orders);
                    return savedCustomerOrder;
                }

            }
            return null;
        }
        catch (IOException e) {
            System.out.println(e);

        }

        return null;

    }


    @CacheEvict(cacheNames = "orders")
    public void deleteOrder( int ordernumber) {
        if(repository.findById(ordernumber)==null){
            throw new OrderNotFoundException("Order with ordernumber "+ordernumber+ " is not present." );
        }
        repository.deleteById(ordernumber);
    }

    @CachePut(cacheNames = "orders")
    public Orders updateOrder(Orders orders)
    {
        //List<Long> l= proxy.retrieveAllIds();
        RestTemplate restTemplate =new RestTemplate();
        Customers[] l= restTemplate.getForObject("http://localhost:8100/customers", Customers[].class);//.execute
        for(Customers i:l)
        {
            if(orders.getCustomerid()==i.getCustomerid())
            {
                Orders updatedCustomer = repository.save(orders);
                return updatedCustomer;
            }
        }
        return null;

    }






}
