package com.example.repository;


import com.example.model.Cart;
import com.example.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class CartRepository extends MainRepository<Cart> {
    private static final String CART_PATH = "src/main/java/com/example/data/carts.json";

    public CartRepository(){}

    @Override
    protected String getDataPath() {
        return CART_PATH;
    }

    @Override
    protected Class<Cart[]> getArrayType() {
        return Cart[].class;
    }

    public Cart addCart(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }

        ArrayList<Cart> carts = findAll();

        if (cart.getId() == null) {
            cart.setId(UUID.randomUUID());
        }

        for (Cart existingCart : carts) {
            if (existingCart.getUserId().equals(cart.getUserId())) {
                throw new RuntimeException("Cart already exists for user with ID: " + cart.getUserId());
            }
        }

        carts.add(cart);
        saveAll(carts);

        return cart;
    }

    public ArrayList<Cart> getCarts() {
        return findAll();
    }

    public Cart getCartById(UUID cartId) {
        for (Cart cart : findAll()) {
            if (cart.getId().equals(cartId)) {
                return cart;
            }
        }
        return null;
    }

    public Cart getCartByUserId(UUID userId) {
        for (Cart cart : findAll()) {
            if (cart.getUserId().equals(userId)) {
                return cart;
            }
        }
        return null;
    }


    public void addProductToCart(UUID cartId, Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        ArrayList<Cart> carts = findAll();

        Cart cartToUpdate = null;
        for (Cart cart : carts) {
            if (cart.getId().equals(cartId)) {
                cartToUpdate = cart;
                break;
            }
        }

        if (cartToUpdate == null) {
            throw new RuntimeException("Cart not found with ID: " + cartId);
        }

        cartToUpdate.getProducts().add(product);
        saveAll(carts);
    }

    public void deleteProductFromCart(UUID cartId, Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        ArrayList<Cart> carts = findAll();
        Cart cartToUpdate = null;

        for (Cart cart : carts) {
            if (cart.getId().equals(cartId)) {
                cartToUpdate = cart;
                break;
            }
        }

        if (cartToUpdate == null) {
            throw new RuntimeException("Cart not found with ID: " + cartId);
        }

        boolean removed = false;
        List<Product> products = cartToUpdate.getProducts();

        for (Product p : products) {
            if (p.getId().equals(product.getId())) {
                products.remove(p);
                removed = true;
                break;
            }
        }
        if (!removed) {
            throw new RuntimeException("Product not found in cart with ID: " + product.getId());
        }

        if (products.isEmpty()) {
            throw new RuntimeException("Cart got empty");
        }

        saveAll(carts);
    }

    public void clearCartAfterCheckout(UUID cartId) {
        ArrayList<Cart> carts = findAll();
        Cart cartToUpdate = null;

        for (Cart cart : carts) {
            if (cart.getId().equals(cartId)) {
                cartToUpdate = cart;
                break;
            }
        }

        if (cartToUpdate == null) {
            throw new RuntimeException("Cart not found with ID: " + cartId);
        }

        List<Product> products = cartToUpdate.getProducts();
        Iterator<Product> iterator = products.iterator();

        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

        saveAll(carts);
    }


    public void deleteCartById(UUID cartId) {
        ArrayList<Cart> carts = findAll();

        Cart cartToDelete = null;
        for (Cart cart : carts) {
            if (cart.getId().equals(cartId)) {
                cartToDelete = cart;
                break;
            }
        }

        if (cartToDelete == null) {
            throw new RuntimeException("Cart not found with ID: " + cartId);
        }
        carts.remove(cartToDelete);
        saveAll(carts);
    }






}
