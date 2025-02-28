package com.example.controller;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.service.CartService;
import com.example.service.ProductService;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/")
    public User addUser(@RequestBody User user){
       return userService.addUser(user);
    }

    @GetMapping("/")
    public ArrayList<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/{userId}/orders")
    public List<Order> getOrdersByUserId(@PathVariable UUID userId){
        return userService.getOrdersByUserId(userId);
    }

    @PostMapping("/{userId}/checkout")
    public  String  addOrderToUser(@PathVariable  UUID  userId){
        Cart cart = cartService.getCartByUserId(userId);
        Order order = new Order();
        order.setUserId(userId);
        order.setProducts(cart.getProducts());
        userService.addOrderToUser(userId ,order);
        //TODO:check tests for string
        return "Order added successfully";
    }

    @PostMapping("/{userId}/removeOrder")
    public String removeOrderFromUser(@PathVariable UUID userId, @RequestParam UUID orderId){
        userService.removeOrderFromUser(userId, orderId);
        return "Order removed successfully";
    }

    @DeleteMapping("/{userId}/emptyCart")
    public String emptyCart(@PathVariable UUID userId){
        userService.emptyCart(userId);
        return "Cart emptied successfully";
    }

    @PutMapping("/addProductToCart")
    public String addProductToCart(@RequestParam UUID userId, @RequestParam UUID productId){
        Cart cart = cartService.getCartByUserId(userId);

        if(cart == null){
            cart = new Cart();
            cart.setUserId(userId);
            cartService.addCart(cart);
        }
        Product product = productService.getProductById(productId);
        cartService.addProductToCart(cart.getId(),product);
        return "Product added to cart";
    }

    @PutMapping("/deleteProductFromCart")
    public String deleteProductFromCart(@RequestParam UUID userId, @RequestParam UUID productId){
        Cart cart = cartService.getCartByUserId(userId);
        Product product = productService.getProductById(productId);
        cartService.deleteProductFromCart(cart.getId(),product);
        //TODO: testDeleteProductFromCartEndPoint2 wants "Cart is Empty"
        return "Product deleted from cart";
    }


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