package com.example.controller;

import com.example.model.Product;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

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
    public ResponseEntity<Object> updateProduct(@PathVariable UUID productId, @RequestBody Map<String, Object> body) {
        String newName = body.get("newName").toString();
        double newPrice = (double) body.get("newPrice");

        try {
            Product updatedProduct = productService.updateProduct(productId, newName, newPrice);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found");
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
