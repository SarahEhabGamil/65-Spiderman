package com.example.repository;


import com.example.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class OrderRepository extends MainRepository<Order>{
    private static final String ORDER_PATH = "data/orders.json";

    public OrderRepository() {}
    @Override
    protected String getDataPath() {
        return ORDER_PATH;
    }

    @Override
    protected Class<Order[]> getArrayType() {
        return Order[].class;
    }

    public void addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        ArrayList<Order> orders = findAll();

        if (order.getId() == null) {
            order.setId(UUID.randomUUID());
        }
        orders.add(order);
        saveAll(orders);
    }

    public ArrayList<Order> getOrders() {
        return findAll();
    }

    public Order getOrderById(UUID orderId) {
        for (Order order : findAll()) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }


    public void deleteOrderById(UUID orderId) {
        ArrayList<Order> orders = findAll();

        Order orderToDelete = null;
        for (Order order : orders) {
            if (order.getId().equals(orderId)) {
                orderToDelete = order;
                break;
            }
        }

        if (orderToDelete == null) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
        orders.remove(orderToDelete);
        saveAll(orders);
    }
}
