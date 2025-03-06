package com.example.service;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class CartService extends MainService{
    private final CartRepository cartRepository;
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    private boolean userExists(UUID userId) {
        return cartRepository.getCarts().stream().anyMatch(user -> user.getId().equals(userId));
    }

    // Service Layer
    public Cart addCart(Cart cart) throws Exception {
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }
        if (cart.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        ArrayList<Cart> carts = cartRepository.findAll();
        for (Cart existingCart : carts) {
            if (existingCart.getUserId().equals(cart.getUserId())) {
                throw new RuntimeException("Cart already exists for user with ID: " + cart.getUserId());
            }
        }
        if (cart.getId() == null) {
            cart.setId(UUID.randomUUID());
        }
        carts.add(cart);
        cartRepository.saveAll(carts);
        return cart;
    }

    public ArrayList<Cart> getCarts(){
        return cartRepository.getCarts();
    }
    public Cart getCartById(UUID cartId){
        return cartRepository.getCartById(cartId);
    }
    public Cart getCartByUserId(UUID userId){
        return cartRepository.getCartByUserId(userId);
    }
    public void addProductToCart(UUID cartId, Product product){
        cartRepository.addProductToCart(cartId, product);
    }
    public void deleteProductFromCart(UUID cartId, Product product) throws Exception{
        cartRepository.deleteProductFromCart(cartId, product);
    }
    public void deleteCartById(UUID cartId){
        cartRepository.deleteCartById(cartId);
    }
    public void clearCartAfterCheckout(UUID cartId){
        cartRepository.clearCartAfterCheckout(cartId);
    }
}
