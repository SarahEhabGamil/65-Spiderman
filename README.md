# Mini1 - Docker Setup Guide

##  Running the Application with Docker

Follow these steps to build, run, and test the application inside a Docker container.

### 1️⃣ Build the Docker Image
```sh
docker build -t mini1 .
```

### 2️⃣ Run the Container
```sh
docker run -d -p 8080:8080 \            
   -v "$(pwd)/src/main/java/com/example/data:/app/data" \
   -e USERS_FILE_PATH=/app/data/users.json \
   -e PRODUCTS_FILE_PATH=/app/data/products.json \
   -e CARTS_FILE_PATH=/app/data/carts.json \
   -e ORDERS_FILE_PATH=/app/data/orders.json \
   --name mini1-container mini1
```

### 3️⃣ Run Tests Inside the Container
```sh
docker exec -it mini1-container mvn test
```

---


