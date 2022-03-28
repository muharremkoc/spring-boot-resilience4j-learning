package com.example.springbootresiliance4jlearning2.controller;

import com.example.springbootresiliance4jlearning2.dto.Order;
import com.example.springbootresiliance4jlearning2.dto.OrderResponseList;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item-service")
public class ItemController {


    @Lazy
    private final RestTemplate restTemplate;

    public static final String ITEM_SERVICE="itemService";

    private static final String BASEURL = "http://localhost:4521/orders";

    private static final ObjectMapper mapper = new ObjectMapper();

    private int attempt=1;


    @GetMapping("/item")
    public String getItem(){
        return "Item  Selected";
    }

    /*
    @GetMapping("/displayOrders")
     //@CircuitBreaker(name =ITEM_SERVICE,fallbackMethod = "orderFallback")
    @Retry(name = ITEM_SERVICE)
    public List<Order> displayOrders(@RequestParam("category") String category) {

        OrderResponseList response = restTemplate.getForObject(
                BASEURL+"/"+category,
                OrderResponseList.class);
        assert response != null;
        List<Order> orderList = response.getOrderList();
        System.out.println("retry method called "+attempt++ +" times "+" at "+new Date());
        return orderList;
    }

     */
    @GetMapping("/displayOrders")
    @CircuitBreaker(name =ITEM_SERVICE,fallbackMethod = "orderFallBack")
    //@Retry(name = ITEM_SERVICE,fallbackMethod = "orderFallback")
    public List<Order> processUserDataFromUserList(@RequestParam String category) {
        ResponseEntity<List<Order>> responseEntity =
                restTemplate.exchange(
                        BASEURL+"/"+category,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Order>>() {}
                );
        List<Order> orders = responseEntity.getBody();
        assert orders != null;
        return orders;
    }

    public List<Order> orderFallBack(Exception e) throws NotFoundException {

        String errorMessage=String.format(" %s \n%s","Order Service downed",e.getMessage());

         throw new NotFoundException(errorMessage);

    }


        /* OPTIONAL
    public List<Order> orderFallback(Exception e){
        return Stream.of(
                new Order(119, "LED TV", "electronics"),
                new Order(345, "Headset", "electronics"),
                new Order(475, "Sound bar", "electronics"),
                new Order(574, "Puma Shoes", "foot wear"),
                new Order(678, "Vegetable chopper", "kitchen"),
                new Order(532, "Oven Gloves", "kitchen")
        ).collect(Collectors.toList());
    }

     */



}
