package com.example.repository;

import com.example.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public class ProductRepository extends MainRepository<Product>{
//    private static final String PRODUCT_PATH = "src/main/java/com/example/data/products.json";
private static final String PRODUCT_PATH = System.getenv("PRODUCTS_FILE_PATH"); //
    public ProductRepository() {}

    @Override
    protected String getDataPath() {
        return PRODUCT_PATH;
    }

    @Override
    protected Class<Product[]> getArrayType() {
        return Product[].class;
    }


    public Product addProduct(Product product) {
        if (product == null || product.getName() == null || product.getPrice()==0) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        ArrayList<Product> products = findAll();
        for (Product existingProduct : products) {
            if (existingProduct.getName().equalsIgnoreCase(product.getName())) {
                throw new RuntimeException("Product with name '" + product.getName() + "' already exists.");
            }
        }

        if (product.getId() == null) {
            product.setId(UUID.randomUUID());
        }

        products.add(product);
        saveAll(products);
        return product;
    }

    public ArrayList<Product> getProducts() {
        return findAll();
    }

    public Product getProductById(UUID productId) {
        for (Product product : findAll()) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public Product updateProduct(UUID productId, String newName, double newPrice) {
        ArrayList<Product> products = findAll();
        Product productToUpdate = null;
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                productToUpdate = product;
                break;
            }
        }

        if (productToUpdate == null) {
            throw new RuntimeException("Product not found");
        }else{

            productToUpdate.setName(newName);
            productToUpdate.setPrice(newPrice);
            saveAll(products);
            return productToUpdate;
        }


    }

    public void applyDiscount(double discount, ArrayList<UUID> productIds) throws Exception {
        if (discount <= 0 || discount > 100) {
            throw new IllegalArgumentException("Discount must be between 1 and 100");
        }

        ArrayList<Product> products = findAll();
        boolean updated = false;
        for (Product product : products) {
            if (productIds.contains(product.getId())) {
                double discountedPrice = product.getPrice() - (product.getPrice() * discount / 100);
                product.setPrice(discountedPrice);
                updated = true;
            }
        }
        if (!updated) {
            throw new RuntimeException("No matching products found for the given IDs");
        }
        saveAll(products);
    }


    public void deleteProductById(UUID productId)throws Exception {
        ArrayList<Product> products = findAll();
        Product productToDelete = null;
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                productToDelete = product;
                break;
            }
        }

        if (productToDelete == null) {
            throw new RuntimeException("Product not found");
        }
        products.remove(productToDelete);
        saveAll(products);
    }

}
