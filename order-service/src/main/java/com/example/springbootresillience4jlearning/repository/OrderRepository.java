package com.example.springbootresillience4jlearning.repository;

import com.example.springbootresillience4jlearning.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    List<Order> findByCategory(String category);
}
