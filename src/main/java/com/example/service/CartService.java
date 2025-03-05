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

    public Cart addCart(Cart cart){
        return cartRepository.addCart(cart);
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

//package com.example.service;
//
//import com.example.model.Cart;
//import com.example.model.Product;
//import com.example.repository.CartRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class CartService extends MainService<Cart> {
//
//    private final CartRepository cartRepository;
//
//    public CartService(CartRepository cartRepository) {
//        this.cartRepository = cartRepository;
//    }
//
//    @Override
//    protected UUID getId(Cart cart) {
//        return cart.getId();
//    }
//
//    @Override
//    protected List<Cart> findAll() {
//        return cartRepository.getCarts();
//    }
//
//    @Override
//    protected Cart save(Cart cart) {
//        return cartRepository.addCart(cart);
//    }
//
//    @Override
//    protected void deleteById(UUID id) {
//        cartRepository.deleteCartById(id);
//    }
//
//    public Cart getCartByUserId(UUID userId) {
//        return cartRepository.getCartByUserId(userId);
//    }
//
//    public void addProductToCart(UUID cartId, Product product) {
//        cartRepository.addProductToCart(cartId, product);
//    }
//
//    public void deleteProductFromCart(UUID cartId, Product product) throws Exception {
//        cartRepository.deleteProductFromCart(cartId, product);
//    }
//
//    public void clearCartAfterCheckout(UUID cartId) {
//        cartRepository.clearCartAfterCheckout(cartId);
//    }
//}