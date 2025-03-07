package com.example.repository;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository extends MainRepository<User>{
    private static final String USER_PATH = "src/main/java/com/example/data/users.json";
//    private static final String USER_PATH = System.getenv("USERS_FILE_PATH");


    public UserRepository() {
    }

    @Override
    protected String getDataPath() {
        return USER_PATH;
    }

    @Override
    protected Class<User[]> getArrayType() {
        return User[].class;
    }

    public ArrayList<User> getUsers() {
        return findAll();
    }

    public User getUserById(UUID userId) {
        for (User user : findAll()) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }


    public User addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        ArrayList<User> users = findAll();
        for (User existingUser : users) {
            if (existingUser.getId().equals(user.getId())) {
                throw new RuntimeException("User with ID '" + user.getId() + "' already exists.");
            }
            if (existingUser.getName().equalsIgnoreCase(user.getName())) {
                throw new RuntimeException("User with name '" + user.getName() + "' already exists.");
            }
        }
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
        users.add(user);
        saveAll(users);
        return user;
    }


    public List<Order> getOrdersByUserId(UUID userId) {
        User user = getUserById(userId);
        if (user == null) {
            return null;
        }
        return user.getOrders();
    }


    public void addOrderToUser(UUID userId, Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        User user = getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        user.addOrder(order);
        ArrayList<User> users = findAll();
        boolean userUpdated = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(userId)) {
                users.set(i, user);
                userUpdated = true;
                break;
            }
        }
        if (!userUpdated) {
            throw new RuntimeException("Failed to update user data");
        }
        saveAll(users);
    }

    public void removeOrderFromUser(UUID userId, UUID orderId) {
        User user = getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        List<Order> orders = user.getOrders();
        Order orderToRemove = null;
        for (Order order : orders) {
            if (order.getId().equals(orderId)) {
                orderToRemove = order;
                break;
            }
        }
        if (orderToRemove == null) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
        orders.remove(orderToRemove);
        ArrayList<User> users = findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(userId)) {
                users.set(i, user);
                break;
            }
        }
        saveAll(users);
    }
    public void deleteUserById(UUID userId) {
        ArrayList<User> users = findAll();
        User userToDelete = null;
        for (User user : users) {
            if (user.getId().equals(userId)) {
                userToDelete = user;
                break;
            }
        }

        if (userToDelete == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        users.remove(userToDelete);
        saveAll(users);
    }
}