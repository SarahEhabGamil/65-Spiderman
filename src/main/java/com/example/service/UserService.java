package com.example.service;

import com.example.model.Order;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public List<Order> getOrdersByUserId(UUID userId) {
        return userRepository.getOrdersByUserId(userId);
    }
    public void addOrderToUser(UUID userId, Order order) {
        userRepository.addOrderToUser(userId, order); //lesa mehtag the logic
    }
    public void emptyCart(UUID userId) {

    }
    public void removeOrderFromUser(UUID userId, UUID orderId){
        userRepository.removeOrderFromUser(userId, orderId);
    }
    public void deleteUserById(UUID userId){
        userRepository.deleteUserById(userId);
    }

}
