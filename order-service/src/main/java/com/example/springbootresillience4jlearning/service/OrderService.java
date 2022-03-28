package com.example.springbootresillience4jlearning.service;

import com.example.springbootresillience4jlearning.dto.OrderDto;
import com.example.springbootresillience4jlearning.model.Order;

import java.util.List;

public interface OrderService {

    Order saveOrder(OrderDto orderDto);
    List<Order> getAllOrders();
    List<Order> getAllOrdersByCategory(String category);
}
