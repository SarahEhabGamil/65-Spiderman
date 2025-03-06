package com.example.controller;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/")
    public Cart addCart(@RequestBody Cart cart)throws Exception{
        try{
            return cartService.addCart(cart);
        }
        catch(Exception e){
            return null;
        }

    }
//    @PostMapping("/")
//    public ResponseEntity<?> addCart(@RequestBody Cart cart) throws Exception {
//        try {
//            Cart addedCart = cartService.addCart(cart);
//            return ResponseEntity.ok(addedCart);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }


    @GetMapping("/")
    public ArrayList<Cart> getCarts(){
        return cartService.getCarts();
    }

    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable UUID cartId){
        return cartService.getCartById(cartId);
    }

    @PutMapping("/addProduct/{cartId}")
    public ResponseEntity<String> addProductToCart(@PathVariable UUID cartId, @RequestBody Product product) {
        try {
            cartService.addProductToCart(cartId, product);
            return ResponseEntity.ok(product.getName() + " added to cart");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<String> deleteCartById(@PathVariable UUID cartId) {
        try {
            cartService.deleteCartById(cartId);
            return ResponseEntity.ok("Cart deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
