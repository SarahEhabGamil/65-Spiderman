package com.example.MiniProject1;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import com.example.service.CartService;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
@ComponentScan(basePackages = "com.example.*")
@WebMvcTest
class MiniProject1ApplicationTests2 {

    @Value("${spring.application.userDataPath}")
    private String userDataPath;

    @Value("${spring.application.productDataPath}")
    private String productDataPath;

    @Value("${spring.application.orderDataPath}")
    private String orderDataPath;

    @Value("${spring.application.cartDataPath}")
    private String cartDataPath;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void overRideAll(){
        try{
            objectMapper.writeValue(new File(userDataPath), new ArrayList<User>());
            objectMapper.writeValue(new File(productDataPath), new ArrayList<Product>());
            objectMapper.writeValue(new File(orderDataPath), new ArrayList<Order>());
            objectMapper.writeValue(new File(cartDataPath), new ArrayList<Cart>());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

    public Object find(String typeString, Object toFind){
        switch(typeString){
            case "User":
                ArrayList<User> users = getUsers();

                for(User user: users){
                    if(user.getId().equals(((User)toFind).getId())){
                        return user;
                    }
                }
                break;
            case "Product":
                ArrayList<Product> products = getProducts();
                for(Product product: products){
                    if(product.getId().equals(((Product)toFind).getId())){
                        return product;
                    }
                }
                break;
            case "Order":
                ArrayList<Order> orders = getOrders();
                for(Order order: orders){
                    if(order.getId().equals(((Order)toFind).getId())){
                        return order;
                    }
                }
                break;
            case "Cart":
                ArrayList<Cart> carts = getCarts();
                for(Cart cart: carts){
                    if(cart.getId().equals(((Cart)toFind).getId())){
                        return cart;
                    }
                }
                break;
        }
        return null;
    }

    public Product addProduct(Product product) {
        try {
            File file = new File(productDataPath);
            ArrayList<Product> products;
            if (!file.exists()) {
                products = new ArrayList<>();
            }
            else {
                products = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Product[].class)));
            }
            products.add(product);
            objectMapper.writeValue(file, products);
            return product;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }
    public ArrayList<Product> getProducts() {
        try {
            File file = new File(productDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<Product>(Arrays.asList(objectMapper.readValue(file, Product[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }

    public User addUser(User user) {
        try {
            File file = new File(userDataPath);
            ArrayList<User> users;
            if (!file.exists()) {
                users = new ArrayList<>();
            }
            else {
                users = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, User[].class)));
            }
            users.add(user);
            objectMapper.writeValue(file, users);
            return user;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }
    public ArrayList<User> getUsers() {
        try {
            File file = new File(userDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<User>(Arrays.asList(objectMapper.readValue(file, User[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }
    public Cart addCart(Cart cart){
        try{
            File file = new File(cartDataPath);
            ArrayList<Cart> carts;
            if (!file.exists()) {
                carts = new ArrayList<>();
            }
            else {
                carts = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Cart[].class)));
            }
            carts.add(cart);
            objectMapper.writeValue(file, carts);
            return cart;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }
    public ArrayList<Cart> getCarts() {
        try {
            File file = new File(cartDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<Cart>(Arrays.asList(objectMapper.readValue(file, Cart[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }
    public Order addOrder(Order order){
        try{
            File file = new File(orderDataPath);
            ArrayList<Order> orders;
            if (!file.exists()) {
                orders = new ArrayList<>();
            }
            else {
                orders = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
            }
            orders.add(order);
            objectMapper.writeValue(file, orders);
            return order;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }
    public ArrayList<Order> getOrders() {
        try {
            File file = new File(orderDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<Order>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }



    private UUID userId;
    private User testUser;
    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        testUser = new User();
        testUser.setId(userId);
        testUser.setName("Test User");
        overRideAll();
    }

    // ------------------------ User Tests -------------------------
    @Test
    void testAddUserWithoutId() throws Exception {
        User newUser = new User();
        newUser.setName("User Without ID");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        User createdUser = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);

        assertNotNull(createdUser.getId(), "User ID should be automatically generated");
        assertEquals(newUser.getName(), createdUser.getName(), "User name should match");
    }

    @Test
    void testAddNullUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    public void clearUsers() {
        try {
            File file = new File("src/main/java/com/example/data/users.json");
            if (file.exists()) {
                objectMapper.writeValue(file, new ArrayList<User>());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to clear user data", e);
        }
    }
    //TODO one more test for Get Users
    @Test
    void testGetUsers_NoUsers() throws Exception {
        clearUsers();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<User> responseUsers = objectMapper.readValue(responseContent, new TypeReference<List<User>>() {});

        assertEquals(0, responseUsers.size(), "No users should be returned when there are no users in the system");
    }

    @Test
    void testGetUserByIdNotFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", nonExistentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }

    @Test
    void testGetUserByIdWithMultipleUsers() throws Exception {
        User testUser1 = new User();
        testUser1.setId(UUID.randomUUID());
        testUser1.setName("Test User1");
        addUser(testUser1);

        User testUser2 = new User();
        testUser2.setId(UUID.randomUUID());
        testUser2.setName("Test User2");
        addUser(testUser2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", testUser2.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        User responseUser = objectMapper.readValue(responseContent, User.class);

        assertEquals(testUser2.getId(), responseUser.getId(), "Fetched user ID should match the requested ID");
        assertEquals(testUser2.getName(), responseUser.getName(), "Fetched user name should match the requested user");
    }

    // ------------------------ Product Tests -------------------------


    @Test
    void testAddProductNullProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetProductsEmptyList() throws Exception {
        List<Product> initialProducts = productService.getProducts();
        assertEquals(0, initialProducts.size(), "Database should be empty before test");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Product> responseProducts = objectMapper.readValue(responseContent, new TypeReference<List<Product>>() {});

        assertEquals(0, responseProducts.size(), "Expected an empty product list from endpoint");
    }

    @Test
    void testGetProductsWithProducts() throws Exception {
        Product testProduct1 = new Product();
        testProduct1.setId(UUID.randomUUID());
        testProduct1.setName("Product A");
        testProduct1.setPrice(15.0);
        addProduct(testProduct1);

        Product testProduct2 = new Product();
        testProduct2.setId(UUID.randomUUID());
        testProduct2.setName("Product B");
        testProduct2.setPrice(25.0);
        addProduct(testProduct2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Product> responseProducts = objectMapper.readValue(responseContent, new TypeReference<List<Product>>() {});

        assertEquals(getProducts().size(), responseProducts.size(), "Products should be returned correctly from endpoint");
    }

    @Test
    void testGetProductByIdExistingProduct() throws Exception {
        Product testProduct = new Product();
        UUID productId = UUID.randomUUID();
        testProduct.setId(productId);
        testProduct.setName("Existing Product");
        testProduct.setPrice(30.0);
        addProduct(testProduct);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Product responseProduct = objectMapper.readValue(responseContent, Product.class);

        assertEquals(productId, responseProduct.getId(), "Returned product ID should match the requested ID");
        assertEquals("Existing Product", responseProduct.getName(), "Returned product name should match");
    }
    @Test
    void testGetProductByIdInvalidId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/product/{productId}", "invalid-id"))
                .andExpect(status().isBadRequest()); // Should return 400
    }

    @Test
    void testGetProductByIdNonExistentProduct() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.get("/product/" + randomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }


    /// ///UPDATEE TESTS LESSAAA
    @Test
    void testUpdateProductProductNotFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("newName", "New Name");
        updateBody.put("newPrice", 100.0);

        mockMvc.perform(MockMvcRequestBuilders.put("/product/update/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBody)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Product not found")));
    }
    @Test
    void testUpdateProductInvalidRequestBody() throws Exception {
        Product testProduct = new Product();
        UUID productId = UUID.randomUUID();
        testProduct.setId(productId);
        testProduct.setName("Old Name");
        testProduct.setPrice(50.0);
        addProduct(testProduct);

        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("newName", "Updated Name");

        mockMvc.perform(MockMvcRequestBuilders.put("/product/update/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBody)))
                .andExpect(status().isBadRequest()); // Expect 400 Bad Request due to missing required fields
    }

    @Test
    void testApplyDiscountInvalidDiscount() throws Exception {
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);
        product.setName("Product C");
        product.setPrice(150.0);
        addProduct(product);
        List<UUID> productIds = Collections.singletonList(productId);
        double invalidDiscount = 0.0;
        mockMvc.perform(MockMvcRequestBuilders.put("/product/applyDiscount")
                        .param("discount", String.valueOf(invalidDiscount))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productIds)))
                .andExpect(status().isOk())
                .andExpect(content().string("Discount must be between 1 and 100"));
    }
    @Test
    void testApplyDiscountNoMatchingProducts() throws Exception {
        UUID nonExistingProductId = UUID.randomUUID();
        List<UUID> productIds = Collections.singletonList(nonExistingProductId);
        double discount = 10.0;

        mockMvc.perform(MockMvcRequestBuilders.put("/product/applyDiscount")
                        .param("discount", String.valueOf(discount))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productIds)))
                .andExpect(status().isOk())
                .andExpect(content().string("No matching products found for the given ID"));
    }
    @Test
    void testDeleteProductByIdInvalidUUID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/invalid-uuid"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testDeleteProductByIdNotFound() throws Exception {
        UUID randomId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/" + randomId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Product not found"));
    }

    // --------------------------------- Cart Tests -------------------------




    // --------------------------------- Order Tests -------------------------

    @Test
    void testAddOrderNullOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void testAddOrderGenerateId() throws Exception {
        Order testOrder = new Order();
        mockMvc.perform(MockMvcRequestBuilders.post("/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrder)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertNotNull(testOrder.getId(), "Order ID should be generated if not provided.");
    }
    /// Third test lesa

    @Test
    void testGetOrdersEmptyList() throws Exception {
        orderRepository.clearOrders();
        mockMvc.perform(MockMvcRequestBuilders.get("/order/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void testGetOrderByIdNotFound() throws Exception {
        UUID nonExistentOrderId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/order/" + nonExistentOrderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetOrderByIdInvalidUUID() throws Exception {
        String invalidUUID = "invalidId";
        mockMvc.perform(MockMvcRequestBuilders.get("/order/" + invalidUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    //	 @Test
//	 void testDeleteOrderById_InvalidId() throws Exception {
//		 mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/{id}", 123))
//				 .andExpect(status().isBadRequest())
//				 .andExpect(content().string("Invalid order ID format"));
//	 }
    @Test
    void testDeleteOrderById_UnexpectedError() throws Exception {
        UUID validOrderId = UUID.randomUUID();
        // Simulate an unexpected error during deletion
        doThrow(new RuntimeException("Unexpected server error")).when(orderService).deleteOrderById(validOrderId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/{id}", validOrderId.toString()))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An unexpected error occurred"));
    }
}