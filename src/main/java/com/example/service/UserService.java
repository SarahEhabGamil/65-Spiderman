package com.example.service;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.User;
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

    public UserService(UserRepository userRepository, CartService cartService) {
        this.userRepository = userRepository;
        this.cartService = cartService;
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
    public List<Order> getOrdersByUserId(UUID userId) throws Exception {
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
        Cart cart = cartService.getCartByUserId(userId);
        UUID cartId = cart.getId();
        cartService.clearCartAfterCheckout(cartId);
    }
    public void removeOrderFromUser(UUID userId, UUID orderId){
        userRepository.removeOrderFromUser(userId, orderId);
    }
    public void deleteUserById(UUID userId){
        userRepository.deleteUserById(userId);
    }

}
