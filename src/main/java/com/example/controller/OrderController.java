package com.example.controller;

import com.example.model.Order;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    public void addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
    }
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable UUID orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        return order;
    }

    @GetMapping("/")
    public ArrayList<Order> getOrders() {
        return orderService.getOrders();
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable UUID orderId) {
        try {
            orderService.deleteOrderById(orderId);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (RuntimeException e) {
            if ("Order not found".equals(e.getMessage())) {
                return ResponseEntity.ok("Order not found");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

}
