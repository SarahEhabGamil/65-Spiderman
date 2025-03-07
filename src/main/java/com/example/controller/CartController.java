package com.example.controller;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.User;
import com.example.service.CartService;
import com.example.service.ProductService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Autowired
    public CartController(CartService cartService,UserService userService,ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping("/")
    public Cart addCart(@RequestBody Cart cart)throws Exception{
            UUID userId = cart.getUserId();
            if(userId == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId is null");
            }
            User user = userService.getUserById(cart.getUserId());
            if(user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        try{
            return cartService.addCart(cart);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add cart`");
        }

    }

    @GetMapping("/")
    public ArrayList<Cart> getCarts(){
        return cartService.getCarts();
    }

    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable UUID cartId){
        return cartService.getCartById(cartId);
    }


    @PutMapping("/addProduct/{cartId}")
    public String addProductToCart(@PathVariable UUID cartId, @RequestBody Product product) {
        UUID checkProductId = product.getId();
        Product checkProduct = productService.getProductById(checkProductId);
        if(checkProduct == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        String productName = product.getName();
        Double productPrice = product.getPrice();
        if(productPrice == null || productName == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "productPrice or productName is null");
        }
        if(productPrice < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "productPrice is negative");
        }
        try {
            cartService.addProductToCart(cartId, product);
            return product.getName() + " added to cart";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to add cart`");
        }
    }
    @DeleteMapping("/delete/{cartId}")
    public String deleteCartById(@PathVariable UUID cartId) {
        try {
            cartService.deleteCartById(cartId);
            return"Cart deleted successfully";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found`");
        }
    }
}
