package com.example.service;


import com.example.model.Order;
import com.example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class OrderService extends MainService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository, CartService cartService, UserService userService) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(Order order)  {
        orderRepository.addOrder(order);
    }
    public ArrayList<Order> getOrders(){
        return orderRepository.getOrders();
    }
    public Order getOrderById(UUID orderId){
        return orderRepository.getOrderById(orderId);
    }
    public void deleteOrderById(UUID orderId) throws IllegalArgumentException {
        orderRepository.deleteOrderById(orderId);
    }
}
