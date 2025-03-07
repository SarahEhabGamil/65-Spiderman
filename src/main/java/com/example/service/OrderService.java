package com.example.service;


import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class OrderService extends MainService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, CartService cartService, UserService userService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public void addOrder(Order order)  {
        List<Product> products = order.getProducts();
        for (Product product : products) {
            Product productToCheck= productService.getProductById(product.getId());
            if(productToCheck==null) {
                throw new IllegalArgumentException("Product not found");
            }
        }
        orderRepository.addOrder(order);
    }
    public ArrayList<Order> getOrders(){
        return orderRepository.getOrders();
    }
    public Order getOrderById(UUID orderId){
        return orderRepository.getOrderById(orderId);
    }
    public void deleteOrderById(UUID orderId) throws IllegalArgumentException {
        Order order = orderRepository.getOrderById(orderId);
        if(order == null){
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteOrderById(orderId);
    }
}
