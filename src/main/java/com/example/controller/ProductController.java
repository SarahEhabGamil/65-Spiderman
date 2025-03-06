package com.example.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.model.Product;
import com.example.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public Product addProduct(@RequestBody Product product) {
            return productService.addProduct(product);
    }

    @GetMapping("/")
    public ArrayList<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable UUID productId) {
        return productService.getProductById(productId);
    }


    @PutMapping("/update/{productId}")
    public Product updateProduct(@PathVariable UUID productId, @RequestBody Map<String, Object> body) {
        if (!body.containsKey("newName") && !body.containsKey("newPrice")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields: newName and newPrice");
        }

        String newName = body.get("newName").toString();
        double newPrice;

        try {
            newPrice = Double.parseDouble(body.get("newPrice").toString());
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid value for newPrice");
        }

        if (newName == null || newName.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "newName cannot be null or empty");
        }

        if (newPrice <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "newPrice must be greater than 0");
        }

        try {
            return productService.updateProduct(productId, newName, newPrice);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
        }
    }

    @PutMapping("/applyDiscount")
    public String applyDiscount(@RequestParam double discount, @RequestBody ArrayList<UUID> productIds) {
        try {
            productService.applyDiscount(discount, productIds);
            return "Discount applied successfully";
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if ("Discount must be between 1 and 100".equals(errorMessage)) {
                return "Discount must be between 1 and 100";
            } else if ("No matching products found for the given IDs".equals(errorMessage)) {
                return "No matching products found for the given ID";
            }
        }
        return "An unexpected error occurred";
    }


    @DeleteMapping("/delete/{productId}")
    public String deleteProductById(@PathVariable UUID productId) throws Exception{
        try{
            productService.deleteProductById(productId);
            return "Product deleted successfully";
        }
        catch(Exception e){
            String errorMessage = e.getMessage();
            if ("Product not found".equals(errorMessage)) {
                return "Product not found";
            }
        }
        return "An unexpected error occurred";

    }
}
