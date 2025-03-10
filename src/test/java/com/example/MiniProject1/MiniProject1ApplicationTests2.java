package com.example.MiniProject1;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
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

    public void clearCarts() {
        try {
            File file = new File("src/main/java/com/example/data/carts.json");
            if (file.exists()) {
                objectMapper.writeValue(file, new ArrayList<Cart>());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to clear cart data", e);
        }
    }
    public void clearProducts() {
        try {
            File file = new File("src/main/java/com/example/data/products.json");
            if (file.exists()) {
                objectMapper.writeValue(file, new ArrayList<Cart>());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to clear cart data", e);
        }
    }


    public void removeCart(UUID cartId) {
        try {
            File file = new File("src/main/java/com/example/data/carts.json");
            if (!file.exists()) {
                throw new RuntimeException("Cart data file not found.");
            }

            ArrayList<Cart> carts = objectMapper.readValue(file, new TypeReference<ArrayList<Cart>>() {});

            boolean removed = carts.removeIf(cart -> cart.getId().equals(cartId));

            if (!removed) {
                throw new RuntimeException("Cart with ID " + cartId + " not found.");
            }

            objectMapper.writeValue(file, carts);
        } catch (IOException e) {
            throw new RuntimeException("Failed to remove cart", e);
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

    //1.1 addUser no ID - PASSED
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

    //1.2 add Null User - PASSED
    @Test
    void testAddUserWithoutName() throws Exception {
        User newUser = new User();
        mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    //1.3 addUser Duplicate - PASSED

    @Test
    void testAddDuplicateUser() throws Exception {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Test User");

        // first user
        mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // duplicate
        mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    //2.1 getUsers no Users - PASSED
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

    //2.2 getUsers correct specific data - PASSED
    @Test
    void testGetUsers_AddAndGetUser() throws Exception {
    clearUsers();
        User testUser = new User();
        UUID testId = UUID.randomUUID();
        testUser.setId(testId);
        testUser.setName("Lilly");

        addUser(testUser);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<User> responseUsers = objectMapper.readValue(responseContent, new TypeReference<List<User>>() {});

        assertFalse(responseUsers.isEmpty(), "User list should not be empty");
        assertEquals(testId, responseUsers.getFirst().getId(), "Returned user should have the correct ID");
        assertEquals("Lilly", responseUsers.getFirst().getName(), "Returned user should have the correct name");
        assertEquals(1, responseUsers.size(), "Returned user should have the correct number of users");
    }

    //2.3 getUsers special characters - PASSED
    @Test
    void testGetUsers_SpecialCharacterNames() throws Exception {
        clearUsers();
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setName("M@RW@N");

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setName("حسن وائل");

        addUser(user1);
        addUser(user2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<User> responseUsers = objectMapper.readValue(responseContent, new TypeReference<List<User>>() {});

        List<String> retrievedNames = responseUsers.stream().map(User::getName).collect(Collectors.toList());

        assertTrue(retrievedNames.contains("M@RW@N"), "Returned users should include names with special characters");
        assertTrue(retrievedNames.contains("حسن وائل"), "Returned users should include names with non-Latin characters");
        assertEquals(2, responseUsers.size(), "Returned users should have the correct number of users");
    }


    //3.1 getUserById ID not found - PASSED
    @Test
    void testGetUserByIdNotFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

      mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", nonExistentId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    //3.2 getUserById multiple users - PASSED
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

    //3.3 getUserById case sensitivity and special characters - PASSED
    @Test
    void testGetUserById_CaseSensitivity() throws Exception {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("MaRw@n");
        addUser(testUser);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", testUser.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        User responseUser = objectMapper.readValue(responseContent, User.class);

        assertEquals("MaRw@n", responseUser.getName(), "Username should retain its original case");
    }

    //4.1 user has no orders - PASSED
    @Test
    void testGetOrdersByUserId_UserHasNoOrders() throws Exception {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Test User");
        addUser(testUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}/orders", testUser.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    //4.2 invalid user id - PASSED
    @Test
    void testGetOrdersByUserId_UserNotFound() throws Exception {
        UUID nonExistentUserId = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}/orders", nonExistentUserId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    //4.3 multiple orders check - PASSED
    @Test
    void testGetOrdersByUserId_MultipleOrders() throws Exception {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Multiple Orders User");

        List<Order> orders = List.of(
                new Order(UUID.randomUUID(), testUser.getId(), 15.0, List.of(new Product(UUID.randomUUID(), "Product A", 15.0))),
                new Order(UUID.randomUUID(), testUser.getId(), 25.0, List.of(new Product(UUID.randomUUID(), "Product B", 25.0)))
        );

        testUser.setOrders(orders);
        addUser(testUser);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}/orders", testUser.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Order> responseOrders = objectMapper.readValue(responseContent, new TypeReference<List<Order>>() {});
        assertEquals(orders.size(), responseOrders.size(), "Orders size should match");
    }



    //5.1 addOrderToUser no user existing - PASSED
    @Test
    void testAddOrderToNonExistentUser() throws Exception {
        UUID nonExistentUserId = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/checkout", nonExistentUserId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }

    //5.2 addOrderToUser user has no cart - PASSED
    @Test
    void testAddOrderToUserWithoutCart() throws Exception {
        User testUser12 = new User();
        testUser12.setId(UUID.randomUUID());
        testUser12.setName("Test User12");
        addUser(testUser12);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/checkout", testUser12.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User has no cart"));
    }


    //5.3 addOrderToUser multiple orders - PASSED
    @Test
    void testAddOrderToUserWithExistingOrders() throws Exception {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Test User");

        Order existingOrder = new Order(UUID.randomUUID(), List.of(new Product(UUID.randomUUID(), "Old Product", 15.0)));
        testUser.addOrder(existingOrder);
        addUser(testUser);

        Cart cart = new Cart();
        Product product = new Product(UUID.randomUUID(), "Product A", 15.0);
        addProduct(product);
        cart.setId(UUID.randomUUID());
        cart.setUserId(testUser.getId());
        cart.setProducts(List.of(product));
        addCart(cart);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/checkout", testUser.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order added successfully"));
    }

    //6.1 removeOrderFromUser non existent user - PASSED
    @Test
    void testRemoveOrderFromNonExistentUser() throws Exception {
        UUID nonExistentUserId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/removeOrder", nonExistentUserId)
                        .param("orderId", orderId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User not found with ID: " + nonExistentUserId));
    }

    //6.2 removeOrderFromUser non existing order - PASSED
    @Test
    void testRemoveNonExistentOrderFromUser() throws Exception {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Test User");
        addUser(testUser);

        UUID nonExistentOrderId = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/removeOrder", testUser.getId())
                        .param("orderId", nonExistentOrderId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order not found with ID: " + nonExistentOrderId));
    }


    //6.3 removeOrderFromUser user has no orders - PASSED
    @Test
    void testRemoveOrderFromUserWithNoOrders() throws Exception {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Test User");
        addUser(testUser);

        UUID randomOrderId = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/removeOrder", testUser.getId())
                        .param("orderId", randomOrderId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Since the response is a String, it will still return 200 OK
                .andExpect(MockMvcResultMatchers.content().string("Order not found with ID: " + randomOrderId));
    }


    //7.1 emptyCart no user - PASSED
    @Test
    void testEmptyCartNonExistentUser() throws Exception {
        UUID nonExistentUserId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}/emptyCart", nonExistentUserId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }

    //7.2 emptyCart user has no cart - PASSED
    @Test
    void testEmptyCartUserWithoutCart() throws Exception {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Test User Without Cart");
        addUser(testUser);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}/emptyCart", testUser.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cart not found"));
    }

    //7.3 emptyCart cart is already empty - PASSED
    @Test
    void testEmptyCartWhenAlreadyEmpty() throws Exception {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Test User with Empty Cart");
        addUser(testUser);

        Cart emptyCart = new Cart(UUID.randomUUID(), testUser.getId(), new ArrayList<>());
        addCart(emptyCart);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}/emptyCart", testUser.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cart is already empty"));
    }


//8.1 addProductToCart user doesnt exist - PASSED
@Test
void testAddProductToCart_UserNotFound() throws Exception {

    Product testProduct = new Product(UUID.randomUUID(), "Test Product", 10.0);
    addProduct(testProduct);

    UUID nonExistentUserId = UUID.randomUUID();

    mockMvc.perform(MockMvcRequestBuilders.put("/user/addProductToCart")
                    .param("userId", nonExistentUserId.toString())
                    .param("productId", testProduct.getId().toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("User not found"));
}

//8.2 addProductToCart product doesn't exist - PASSED
@Test
void testAddProductToCart_ProductNotFound() throws Exception {

    User testUser = new User();
    testUser.setId(UUID.randomUUID());
    testUser.setName("Test User15");
    addUser(testUser);


    UUID nonExistentProductId = UUID.randomUUID();

    mockMvc.perform(MockMvcRequestBuilders.put("/user/addProductToCart")
                    .param("userId", testUser.getId().toString())
                    .param("productId", nonExistentProductId.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Product not found"));
}

//8.3 addProductToCart adding a second product to cart with product - PASSED
@Test
void testAddProductToCart_ExistingCart_AddSecondProduct() throws Exception {
    User testUser = new User();
    testUser.setId(UUID.randomUUID());
    testUser.setName("Test User16");
    addUser(testUser);

    Product firstProduct = new Product(UUID.randomUUID(), "First Product", 15.0);
    addProduct(firstProduct);

    Cart cart = new Cart(UUID.randomUUID(), testUser.getId(), new ArrayList<>(List.of(firstProduct)));
    addCart(cart);

    Product secondProduct = new Product(UUID.randomUUID(), "Second Product", 20.0);
    addProduct(secondProduct);

    mockMvc.perform(MockMvcRequestBuilders.put("/user/addProductToCart")
                    .param("userId", testUser.getId().toString())
                    .param("productId", secondProduct.getId().toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Product added to cart"));

    Cart updatedCart = cartService.getCartByUserId(testUser.getId());
    assertEquals(2, updatedCart.getProducts().size(), "Cart should have 2 products after adding the second product");
}

//9.2 deleteProductFromCart non existent user - PASSED
@Test
void testDeleteProductFromCart_NonExistentUser() throws Exception {

    Product testProduct = new Product(UUID.randomUUID(), "Test Product", 10.0);
    addProduct(testProduct);


    UUID nonExistentUserId = UUID.randomUUID();

    mockMvc.perform(MockMvcRequestBuilders.put("/user/deleteProductFromCart")
                    .param("userId", nonExistentUserId.toString())
                    .param("productId", testProduct.getId().toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Cart is empty"));
}

//9.3 deleteProductFromCart non existent product - PASSED
@Test
void testDeleteProductFromCart_NonExistentProduct() throws Exception {
    // Create and add a user.
    User testUser = new User();
    testUser.setId(UUID.randomUUID());
    testUser.setName("Test User");
    addUser(testUser);
    
    Product validProduct = new Product(UUID.randomUUID(), "Valid Product", 10.0);
    addProduct(validProduct);

    Cart cart = new Cart(UUID.randomUUID(), testUser.getId(), new ArrayList<>(List.of(validProduct)));
    addCart(cart);

    UUID nonExistentProductId = UUID.randomUUID();

    mockMvc.perform(MockMvcRequestBuilders.put("/user/deleteProductFromCart")
                    .param("userId", testUser.getId().toString())
                    .param("productId", nonExistentProductId.toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Product not found"));
}
//9.4 deleteProductFromCart product not in cart - PASSED
@Test
void testDeleteProductFromCart_ProductNotInCart() throws Exception {
    // Create and add a user.
    User testUser = new User();
    testUser.setId(UUID.randomUUID());
    testUser.setName("Test User");
    addUser(testUser);

    Product productInCart = new Product(UUID.randomUUID(), "Product In Cart", 10.0);
    Product productNotInCart = new Product(UUID.randomUUID(), "Product Not In Cart", 15.0);
    addProduct(productInCart);
    addProduct(productNotInCart);


    Cart cart = new Cart(UUID.randomUUID(), testUser.getId(), new ArrayList<>(List.of(productInCart)));
    addCart(cart);

    mockMvc.perform(MockMvcRequestBuilders.put("/user/deleteProductFromCart")
                    .param("userId", testUser.getId().toString())
                    .param("productId", productNotInCart.getId().toString()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Product not in cart"));
}

//10.2 deleteUserById delete twice - PASSED
@Test
void testDeleteUserById_DoubleDeletion() throws Exception {
    User testUser = new User();
    testUser.setId(UUID.randomUUID());
    testUser.setName("Test User19");
    addUser(testUser);

    mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{userId}", testUser.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));

    mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{userId}", testUser.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("User not found"));
}

//10.3 deleteUserById user has cart and orders - PASSED
@Test
void testDeleteUserById_WithDependencies() throws Exception {
    User testUser = new User();
    testUser.setId(UUID.randomUUID());
    testUser.setName("Test User20");
    // Create dependencies for the user.
    Cart cart = new Cart(UUID.randomUUID(), testUser.getId(), new ArrayList<>());
    addCart(cart);
    Order order = new Order(UUID.randomUUID(), testUser.getId(), 100.0, new ArrayList<>());
    testUser.getOrders().add(order);

    addUser(testUser);

    mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{userId}", testUser.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));
}
//10.4 deleteUserById check user list after deletion - PASSED
@Test
void testDeleteUserById_RemovesUserFromList() throws Exception {
    User testUser1 = new User();
    testUser1.setId(UUID.randomUUID());
    testUser1.setName("Test User21");
    addUser(testUser1);

    User testUser2 = new User();
    testUser2.setId(UUID.randomUUID());
    testUser2.setName("Test User22");
    addUser(testUser2);

    mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{userId}", testUser1.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

    String responseContent = result.getResponse().getContentAsString();
    List<User> users = objectMapper.readValue(responseContent, new TypeReference<List<User>>() {});

    boolean userFound = users.stream().anyMatch(user -> user.getId().equals(testUser1.getId()));
    assertFalse(userFound, "Deleted user should not be present in the user list");
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
    void testAddProductMissingFields() throws Exception {
        clearProducts();
        Product testProduct=new Product();
        UUID id1 = UUID.randomUUID();
        testProduct.setId(id1);
        testProduct.setPrice(10.0);
        mockMvc.perform(MockMvcRequestBuilders.post("/product/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isBadRequest());
        Assertions.assertEquals(getProducts().size(), 0);

        Product testProduct2=new Product();
        testProduct2.setId(UUID.randomUUID());
        testProduct2.setName("Test Product2");
        mockMvc.perform(MockMvcRequestBuilders.post("/product/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testProduct2)))
                .andExpect(status().isBadRequest());
        Assertions.assertEquals(getProducts().size(), 0);
    }

    @Test
    void testAddProductNegativePrice() throws Exception {
        clearProducts();
        Product testProduct=new Product();
        testProduct.setId(UUID.randomUUID());
        testProduct.setPrice(-10.0);
        mockMvc.perform(MockMvcRequestBuilders.post("/product/")
                    .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct)))
                    .andExpect(status().isBadRequest());
        Assertions.assertEquals(getProducts().size(), 0);
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
    void testGetProductsWithOneProduct() throws Exception {
        Product testProduct1 = new Product();
        testProduct1.setId(UUID.randomUUID());
        testProduct1.setName("Product A");
        testProduct1.setPrice(15.0);
        addProduct(testProduct1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Product> responseProducts = objectMapper.readValue(responseContent, new TypeReference<List<Product>>() {});

        assertEquals(1, responseProducts.size(), "Products should be returned correctly from endpoint");
    }
    @Test
    void testGetProductsWithMultipleProducts() throws Exception {
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

        assertEquals(2, responseProducts.size(), "Products should be returned correctly from endpoint");
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
                .andExpect(status().isNotFound());
    }
    @Test
    void testUpdateProductMissingFields() throws Exception {
        Product testProduct = new Product();
        UUID productId = UUID.randomUUID();
        testProduct.setId(productId);
        testProduct.setName("Old Name");
        testProduct.setPrice(50.0);
        addProduct(testProduct);

        Map<String, Object> updateBody = new HashMap<>();

        mockMvc.perform(MockMvcRequestBuilders.put("/product/update/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBody)))
                .andExpect(status().isBadRequest());

        List<Product> products = getProducts();

        Product retrievedProduct = products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElse(null);

        assertNotNull(retrievedProduct);
        assertEquals("Old Name", retrievedProduct.getName());
        assertEquals(50.0, retrievedProduct.getPrice());
    }

    @Test
    void testUpdateProductInvalidPrice() throws Exception {

        Product testProduct = new Product();
        UUID productId = UUID.randomUUID();
        testProduct.setId(productId);
        testProduct.setName("Old Name");
        testProduct.setPrice(50.0);
        addProduct(testProduct);

        // update request with an invalid price format
        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("newName", "Updated Name");
        updateBody.put("newPrice", "invalid_price");

        mockMvc.perform(MockMvcRequestBuilders.put("/product/update/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBody)))
                .andExpect(status().isBadRequest());

        List<Product> products = getProducts();


        Product retrievedProduct = products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElse(null);

        assertNotNull(retrievedProduct);
        assertEquals("Old Name", retrievedProduct.getName());
        assertEquals(50.0, retrievedProduct.getPrice());
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
    void testApplyDiscountSomeProductsNotFound() throws Exception {

        Product existingProduct = new Product(UUID.randomUUID(), "Laptop", 1500.0);
        productRepository.save(existingProduct);

        UUID nonExistingProductId = UUID.randomUUID();
        List<UUID> productIds = List.of(existingProduct.getId(), nonExistingProductId);
        double discount = 10.0;

        mockMvc.perform(MockMvcRequestBuilders.put("/product/applyDiscount")
                        .param("discount", String.valueOf(discount))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productIds)))
                .andExpect(status().isOk())
                .andExpect(content().string("One or more product IDs do not exist"));
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

    @Test
    void testDeleteProductTwice() throws Exception {
        Product existingProduct = new Product(UUID.randomUUID(), "Tablet", 500.0);
        productRepository.save(existingProduct);

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/" + existingProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/" + existingProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Product not found"));
    }


    // --------------------------------- Cart Tests -------------------------
    //1.1 Add cart to non existing user - PASSED
    @Test
    void testAddCartForNonExistentUser() throws Exception {
        UUID nonExistentUserId = UUID.randomUUID();

        Cart newCart = new Cart(UUID.randomUUID(), nonExistentUserId, new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.post("/cart/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCart)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        boolean found = getCarts().stream().anyMatch(cart -> cart.getUserId().equals(nonExistentUserId));
        assertFalse(found, "A cart should not be added for a non-existent user.");
    }
    //1.2 add cart with invalid cart object - PASSED
    @Test
    void testAddCartWithInvalidPayload() throws Exception {
        clearCarts();
        String invalidCartJson = "{}";

        mockMvc.perform(MockMvcRequestBuilders.post("/cart/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCartJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        int numberOfCarts = getCarts().size();
        assertEquals(numberOfCarts, 0);
    }
    //1.3 add cart without user id - PASSED
    @Test
    void testAddCartWithoutUserId() throws Exception {
        Cart newCart = new Cart(UUID.randomUUID(), null, new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.post("/cart/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCart)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    //2.1 get carts empty list - PASSED
    @Test
    void testGetCartsWhenEmpty() throws Exception {

        clearCarts();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Cart> responseCarts = objectMapper.readValue(responseContent, new TypeReference<List<Cart>>() {});

        assertTrue(responseCarts.isEmpty(), "Response should be an empty list when no carts exist.");
    }
    //2.2 get multiple carts - PASSED
    @Test
    void testGetMultipleCarts() throws Exception {
        clearCarts();
        Cart cart1 = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
        Cart cart2 = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());

        addCart(cart1);
        addCart(cart2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Cart> responseCarts = objectMapper.readValue(responseContent, new TypeReference<List<Cart>>() {});

        assertEquals(2, responseCarts.size(), "Should return exactly 2 carts.");
    }
    //2.3 get 1 cart - PASSED
    @Test
    void testGetCartsAfterDeletion() throws Exception {
        clearCarts();
        Cart cart1 = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
        Cart cart2 = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());

        addCart(cart1);
        addCart(cart2);
        removeCart(cart1.getId());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Cart> responseCarts = objectMapper.readValue(responseContent, new TypeReference<List<Cart>>() {});

        assertEquals(1, responseCarts.size(), "Only one cart should remain after deletion.");
    }
    //3.1 get cart by random uuid - PASSED
    @Test
    void testGetCartByIdNotFound() throws Exception {
        UUID nonExistentCartId = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.get("/cart/{id}", nonExistentCartId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    //3.2 get cart by id after deleting - PASSED
    @Test
    void testGetCartByIdAfterDeletion() throws Exception {
        Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
        addCart(cart);
        removeCart(cart.getId());

        mockMvc.perform(MockMvcRequestBuilders.get("/cart/{id}", cart.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    //3.1 get cart by id after clearing carts - PASSED
    @Test
    void testGetCartByIdAfterClearingCarts() throws Exception {
        Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
        addCart(cart);

        clearCarts();

        mockMvc.perform(MockMvcRequestBuilders.get("/cart/{id}", cart.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //4.1 add product to cart success - PASSED
    @Test
    void testAddProductToCartSuccess() throws Exception {
        Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
        addCart(cart);

        Product product = new Product(UUID.randomUUID(), "Test Product", 10.99);
        addProduct(product);

        mockMvc.perform(MockMvcRequestBuilders.put("/cart/addProduct/{cartId}", cart.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(product.getName() + " added to cart"));

        boolean productExists = getCarts().stream()
                .filter(c -> c.getId().equals(cart.getId()))
                .flatMap(c -> c.getProducts().stream())
                .anyMatch(p -> p.getId().equals(product.getId()));

        assertTrue(productExists, "Product should be added to the cart.");
    }

    //4.2 add product to non existing cart - PASSED
    @Test
    void testAddProductToNonExistentCart() throws Exception {
        UUID nonExistentCartId = UUID.randomUUID();
        Product product = new Product(UUID.randomUUID(), "Test Product", 10.99);

        mockMvc.perform(MockMvcRequestBuilders.put("/cart/addProduct/{cartId}", nonExistentCartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //4.3 test add null products to cart - PASSED
    @Test
    void testAddNullProductToCart() throws Exception {
        Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
        addCart(cart);

        String nullProductJson = "null";

        mockMvc.perform(MockMvcRequestBuilders.put("/cart/addProduct/{cartId}", cart.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nullProductJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    //5.1 delete non-existing cart - PASSED
    @Test
    void testDeleteNonExistentCart() throws Exception {
        UUID nonExistentCartId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/cart/delete/{id}", nonExistentCartId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //5.2 delete cart twice - PASSED
    @Test
    void testDeleteCartTwice() throws Exception {
        Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
        addCart(cart);
        //first delete
        mockMvc.perform(MockMvcRequestBuilders.delete("/cart/delete/{id}", cart.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cart deleted successfully"));
        //second delete
        mockMvc.perform(MockMvcRequestBuilders.delete("/cart/delete/{id}", cart.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //delete cart after clearing - PASSED
    @Test
    void testDeleteCartAfterClearingCarts() throws Exception {
        Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
        addCart(cart);

        clearCarts();

        mockMvc.perform(MockMvcRequestBuilders.delete("/cart/delete/{id}", cart.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    // --------------------------------- Order Tests -------------------------

    //1.1 - null order - PASSED
    @Test
    void testAddOrderNullOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // 1.1 - Automatically Generates Random ID - PASSED
    @Test
    void testAddOrderGenerateId() throws Exception {
        Order testOrder = new Order();
        mockMvc.perform(MockMvcRequestBuilders.post("/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrder)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertNotNull(testOrder.getId(), "Order ID should be generated if not provided.");
    }

    // 1.3 - Negative Price - PASSED
    @Test
    void testAddOrderNegativeTotalPrice() throws Exception {
        Order testOrder = new Order();
        testOrder.setTotalPrice(-1.0);
        System.out.println("Test Order: "+ testOrder.getTotalPrice());
        testOrder.setId(UUID.randomUUID());
        testOrder.setProducts(new ArrayList<>());

        System.out.println("Test Order: "+ testOrder.getTotalPrice());
        mockMvc.perform(MockMvcRequestBuilders.post("/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrder)))
                .andExpect(status().isBadRequest());
    }

    // 2.1 - No Orders - PASSED
    @Test
    void testGetOrdersEmptyList() throws Exception {
        orderRepository.clearOrders();
        mockMvc.perform(MockMvcRequestBuilders.get("/order/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    // 2.2 - Gets all Orders - PASSED
    @Test
    void testGetOrdersAllOrders() throws Exception {
        orderRepository.clearOrders();
        Order testOrder1 = new Order();
        testOrder1.setUserId(UUID.randomUUID());
        testOrder1.setTotalPrice(20.0);
        testOrder1.setProducts(new ArrayList<>());
        addOrder(testOrder1);

        Order testOrder2 = new Order();
        testOrder2.setUserId(UUID.randomUUID());
        testOrder2.setTotalPrice(10.0);
        testOrder2.setProducts(new ArrayList<>());
        addOrder(testOrder2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/order/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<Order> responseOrders = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(2, responseOrders.size(), "Order List should return exactly 2 orders");

    }

    // 2.3 - Gets Updated List After Deletion - PASSED
    @Test
    void testGetOrdersAfterDeletion() throws Exception {
        orderRepository.clearOrders();
        Order testOrder1 = new Order();
        testOrder1.setUserId(UUID.randomUUID());
        testOrder1.setTotalPrice(20.0);
        testOrder1.setProducts(new ArrayList<>());
        addOrder(testOrder1);

        Order testOrder2 = new Order();
        testOrder2.setUserId(UUID.randomUUID());
        testOrder2.setTotalPrice(10.0);
        testOrder2.setProducts(new ArrayList<>());
        addOrder(testOrder2);

        // get list of orders before deletion
        MvcResult resultBefore = mockMvc.perform(MockMvcRequestBuilders.get("/order/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContentBefore = resultBefore.getResponse().getContentAsString();
        List<Order> responseOrdersBefore = objectMapper.readValue(responseContentBefore, new TypeReference<List<Order>>() {});

        // delete test order 1
        mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/{orderId}", testOrder1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order deleted successfully"));


        // get list of orders after deletion
        MvcResult resultAfter = mockMvc.perform(MockMvcRequestBuilders.get("/order/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContentAfter = resultAfter.getResponse().getContentAsString();
        List<Order> responseOrdersAfter = objectMapper.readValue(responseContentAfter, new TypeReference<List<Order>>() {});

        assertEquals(responseOrdersBefore.size()-1, responseOrdersAfter.size(), "Updated List after deletion should have exactly one less order");
    }

    // 2.4 - Gets Updated List After Addition - PASSED
    @Test
    void testGetOrdersAfterAddition() throws Exception {
        orderRepository.clearOrders();
        Order testOrder1 = new Order();
        testOrder1.setUserId(UUID.randomUUID());
        testOrder1.setTotalPrice(20.0);
        testOrder1.setProducts(new ArrayList<>());
        addOrder(testOrder1);

        // get list of orders before addition
        MvcResult resultBefore = mockMvc.perform(MockMvcRequestBuilders.get("/order/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContentBefore = resultBefore.getResponse().getContentAsString();
        List<Order> responseOrdersBefore = objectMapper.readValue(responseContentBefore, new TypeReference<List<Order>>() {});

        Order testOrder2 = new Order();
        testOrder2.setUserId(UUID.randomUUID());
        testOrder2.setTotalPrice(10.0);
        testOrder2.setProducts(new ArrayList<>());

        // add test order 2
        mockMvc.perform(MockMvcRequestBuilders.post("/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrder2)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // get list of orders after addition
        MvcResult resultAfter = mockMvc.perform(MockMvcRequestBuilders.get("/order/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContentAfter = resultAfter.getResponse().getContentAsString();
        List<Order> responseOrdersAfter = objectMapper.readValue(responseContentAfter, new TypeReference<List<Order>>() {});

        assertEquals(responseOrdersBefore.size()+1, responseOrdersAfter.size(), "Updated List after addition should have exactly one more order");
    }

    //3.1 - Not found - PASSED
    @Test
    void testGetOrderByIdNotFound() throws Exception {
        UUID nonExistentOrderId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/order/" + nonExistentOrderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //3.2 - invalid id - PASSED
    @Test
    void testGetOrderByIdInvalidUUID() throws Exception {
        String invalidUUID = "invalidId";
        mockMvc.perform(MockMvcRequestBuilders.get("/order/" + invalidUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // 3.3 - valid id - PASSED
    @Test
    void testGetOrderByIdSuccess() throws Exception {
        Order testOrder = new Order();
        testOrder.setUserId(UUID.randomUUID());
        testOrder.setTotalPrice(20.0);
        testOrder.setProducts(new ArrayList<>());
        addOrder(testOrder);

        mockMvc.perform(MockMvcRequestBuilders.get("/order/{orderId}", testOrder.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    // 3.4 - correct data - PASSED
    @Test
    void testGetOrderById_CorrectData() throws Exception {
        Order testOrder = new Order();
        testOrder.setUserId(UUID.randomUUID());
        testOrder.setTotalPrice(20.0);
        testOrder.setProducts(new ArrayList<>());
        addOrder(testOrder);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/order/{orderId}", testOrder.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Order responseOrder =  objectMapper.readValue(responseContent, new TypeReference<>() {});

        assertEquals(testOrder.getId(), responseOrder.getId(), "Returned order should have the correct ID");
        assertEquals(testOrder.getUserId(), responseOrder.getUserId(), "Returned order should have the correct user ID");
        assertEquals(testOrder.getTotalPrice(), responseOrder.getTotalPrice(), "Returned order should have the correct total price");
        assertEquals(testOrder.getProducts(), responseOrder.getProducts(), "Returned order should have the correct products list");
    }

    //4.1 - Non-Existent Order - PASSED
    @Test
    void testDeleteOrderById_NotFound() throws Exception {
        UUID validOrderId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/{id}", validOrderId.toString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order not found"));
    }

    //4.2 - invalid id - PASSED
    @Test
    void testDeleteOrderById_InvalidUUID() throws Exception {
        String invalidUUID = "invalidId";
        mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/" + invalidUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    //4.3 - Valid Order removed from order list - PASSED
    @Test
    void testDeleteOrderById_ValidOrder() throws Exception {
        Order validOrder = new Order();
        validOrder.setUserId(UUID.randomUUID());
        validOrder.setTotalPrice(20.0);
        validOrder.setProducts(new ArrayList<>());
        addOrder(validOrder);

        mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/{orderId}", validOrder.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order deleted successfully"));


        //check that order with tested id no longer exists
        mockMvc.perform(MockMvcRequestBuilders.get("/order/{orderId}", validOrder.getId() )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}