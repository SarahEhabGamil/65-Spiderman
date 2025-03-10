package com.example.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class Order {
    private UUID id;
    private UUID userId;
    private double totalPrice;
    private List<Product> products = new ArrayList<>();

    public Order() {
        this.id =  UUID.randomUUID();
    }

    public Order(UUID id, UUID userId, double totalPrice, List<Product> products) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.products = products != null ? products : new ArrayList<>();
    }
    public Order(UUID userId, List<Product> products) {
        this.id =  UUID.randomUUID();
        this.userId = userId;
        this.products = products != null ? products : new ArrayList<>();
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setProducts(List<Product> products) {
        this.products = products != null ? products : new ArrayList<>();
//        recalculateTotalPrice();
    }


    public void addProduct(Product product) {
        if (product != null) {
            this.products.add(product);
            recalculateTotalPrice();
        }
    }

    public void removeProduct(Product product) {
        if (product != null && this.products.contains(product)) {
            this.products.remove(product);
            recalculateTotalPrice();
        }
    }

    private void recalculateTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        this.totalPrice = total;
    }

    public double calculateTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }
    public  double getTotalPrice() {
        return totalPrice;
    }

//    public double totalPriceGetter(){
//        return totalPrice;
//    }
}
