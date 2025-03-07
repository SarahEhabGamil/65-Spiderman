package com.example.service;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class UserService extends MainService{

    private final CartService cartService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository, CartService cartService, CartRepository cartRepository,OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository ;
    }
    public User addUser(User user) {
        return userRepository.addUser(user);
    }
    public User getUserById(UUID id) {
        return userRepository.getUserById(id);
    }
    public ArrayList<User> getUsers() {
        return userRepository.getUsers();
    }
    public List<Order> getOrdersByUserId(UUID userId)throws Exception  {
        List<Order> orders = userRepository.getOrdersByUserId(userId);
        if (orders == null) {
            throw new Exception("User not found");
        }
        return orders;
    }
    public void addOrderToUser(UUID userId, Order order) throws Exception {
        userRepository.addOrderToUser(userId, order);
        this.emptyCart(userId);
    }
    public void emptyCart(UUID userId) throws Exception {
        User user = getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }
        if (cart.getProducts() == null || cart.getProducts().isEmpty()) {
            throw new RuntimeException("Cart is already empty");
        }
        UUID cartId = cart.getId();
        cartService.clearCartAfterCheckout(cartId);
    }
    public void removeOrderFromUser(UUID userId, UUID orderId){
        userRepository.removeOrderFromUser(userId, orderId);
        orderRepository.deleteOrderById(orderId);
    }
    public void deleteUserById(UUID userId){
        userRepository.deleteUserById(userId);
    }

}
