package com.example.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class Cart {
    private UUID id;
    private UUID userId;
    private List<Product> products = new ArrayList<>();
    private double totalPrice;


    //Constructors
    public Cart() {
    }

    public Cart(UUID id, UUID userId, List<Product> products) {
        this.id = id;
        this.userId = userId;
        this.products = products != null ? products : new ArrayList<>();
    }

    public Cart(UUID id, UUID userId, List<Product> products,double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.products = products != null ? products : new ArrayList<>();
        this.totalPrice = totalPrice;
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

    public void setProducts(List<Product> products) {
        this.products = products != null ? products : new ArrayList<>();
    }

    // Helper Methods
    public void addProduct(Product product) {
        if (product != null) {
            this.products.add(product);
        }
    }

    public void removeProduct(Product product) {
        if (product != null) {
            this.products.remove(product);
        }
    }

    public void clearCart() {
        this.products.clear();
    }

//    public double getTotalPrice() {
//        double total = 0;
//        for (Product product : products) {
//            total += product.getPrice();
//        }
//        return total;
//    }

    public double getTotalPrice(){
        return totalPrice;
    }
}
