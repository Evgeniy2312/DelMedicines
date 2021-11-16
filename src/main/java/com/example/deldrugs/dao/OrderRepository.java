package com.example.deldrugs.dao;

import com.example.deldrugs.entity.order.Order;
import com.example.deldrugs.entity.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrdersByOrderStatus(OrderStatus orderStatus);
    List<Order> getOrdersByUserId(long userId);
}
