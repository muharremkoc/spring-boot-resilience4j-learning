package com.example.springbootresillience4jlearning.service;

import com.example.springbootresillience4jlearning.dto.OrderDto;
import com.example.springbootresillience4jlearning.model.Order;
import com.example.springbootresillience4jlearning.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService{

    final OrderRepository orderRepository;

    @Override
    public Order saveOrder(OrderDto orderDto) {
        Order order= Order.builder()
                .name(orderDto.getName())
                .category(orderDto.getCategory())
                .build();
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getAllOrdersByCategory(String category) {
        return orderRepository.findByCategory(category);
    }
}
