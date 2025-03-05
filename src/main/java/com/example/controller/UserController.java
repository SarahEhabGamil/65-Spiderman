package com.example.controller;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.service.CartService;
import com.example.service.ProductService;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;
    private final CartService cartService;
    private final User user;
    private final ProductService productService;

    public UserController(UserService userService, CartService cartService, User user, ProductService productService) {
        this.userService = userService;
        this.cartService = cartService;
        this.user = user;
        this.productService = productService;
    }

    //1
    @PostMapping("/")
    public User addUser(@RequestBody User user){
        try {
            return userService.addUser(user);
        } catch (RuntimeException e) {
            if(e.getMessage().contains("already exists")) {
                //  throws 409
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
            throw e;
        }
    }

    //2
    @GetMapping("/")
    public ArrayList<User> getUsers(){
        return userService.getUsers();
    }

    //3
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId){
        try {
            return userService.getUserById(userId);
        }catch (Exception e){
            return null;
        }
    }
    //4
    @GetMapping("/{userId}/orders")
    public String getOrdersByUserId(@PathVariable UUID userId) {
        try {
            List<Order> orders = userService.getOrdersByUserId(userId);
            if (orders == null || orders.isEmpty()) {
                return "User has no orders";
            }
            return "User orders retrieved successfully";
        } catch (Exception e) {
            return "User not found";
        }
    }
    //5
    @PostMapping("/{userId}/checkout")
    public  String  addOrderToUser(@PathVariable  UUID  userId) throws Exception {
        User user = userService.getUserById(userId);
        if (user == null) {
            return "User not found";
        }

        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null || cart.getProducts().isEmpty()) {
            return "User has no cart";
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setProducts(cart.getProducts());
        userService.addOrderToUser(userId, order);

        return "Order added successfully";
    }

    //6
    @PostMapping("/{userId}/removeOrder")
    public String removeOrderFromUser(@PathVariable UUID userId, @RequestParam UUID orderId){
        try {
            userService.removeOrderFromUser(userId, orderId);
            return "Order removed successfully";
        } catch (RuntimeException e) {
            if (e.getMessage().contains("User not found")) {
                return "User not found with ID: " + userId;
            } else if (e.getMessage().contains("Order not found")) {
                return "Order not found with ID: " + orderId;
            }
            return "An unexpected error occurred";
        }
    }

    //7
    @DeleteMapping("/{userId}/emptyCart")
    public String emptyCart(@PathVariable UUID userId) throws Exception {
        try {
            userService.emptyCart(userId);
            return "Cart emptied successfully";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    //8
    @PutMapping("/addProductToCart")
    public String addProductToCart(@RequestParam UUID userId, @RequestParam UUID productId){
        User user = userService.getUserById(userId);
        if (user == null) {
            return "User not found";
        }

        // Check if the product exists
        Product product = productService.getProductById(productId);
        if (product == null) {
            return "Product not found";
        }

        // Get the cart for the user, or create one if it doesn't exist
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cartService.addCart(cart);
        }

        // Add the product to the cart
        cartService.addProductToCart(cart.getId(), product);
        return "Product added to cart";
    }

    //9
    @PutMapping("/deleteProductFromCart")
    public String deleteProductFromCart(@RequestParam UUID userId, @RequestParam UUID productId) {
        try {
            // Check if the user has a cart. If not, assume user is not found.
            Cart cart = cartService.getCartByUserId(userId);
            if (cart == null) {
                return "User not found";
            }

            // Check if the product exists.
            Product product = productService.getProductById(productId);
            if (product == null) {
                return "Product not found";
            }

            // Attempt to delete the product from the cart.
            cartService.deleteProductFromCart(cart.getId(), product);
            return "Product deleted from cart";
        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg.equals("Cart got empty")) {
                return "Cart is empty";
            } else if (msg.startsWith("Product not found in cart")) {
                return "Product not in cart";
            }
        }
        return "An unexpected error occurred";
    }


    //10
    @DeleteMapping("/delete/{userId}")
    public String deleteUserById(@PathVariable UUID userId){
        try {
            userService.deleteUserById(userId);
        }catch (RuntimeException e){
            return "User not found";
        }
        return "User deleted successfully";
    }





}