package com.example.springbootresillience4jlearning.controller;

import com.example.springbootresillience4jlearning.dto.OrderDto;
import com.example.springbootresillience4jlearning.model.Order;
import com.example.springbootresillience4jlearning.service.OrderService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderController {



   final OrderService orderService;

  @PostMapping("/save")
    public Order saveOrder(@RequestBody OrderDto orderDto){
      return orderService.saveOrder(orderDto);
  }

    @GetMapping("/getAll")
    public List<Order> getOrders(){
        return orderService.getAllOrders();
    }
    @GetMapping("/{category}")
    public List<Order> getOrdersByCategory(@PathVariable String category){
        return orderService.getAllOrdersByCategory(category);
    }

}
