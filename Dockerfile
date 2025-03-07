# Use OpenJDK as the base image
FROM openjdk:25-ea-4-jdk-oraclelinux9

# Set working directory inside the container
WORKDIR /app

# Copy the compiled JAR file
COPY target/mini1.jar /app/mini1.jar

# Copy JSON files to a dedicated directory inside the container
COPY src/main/java/com/example/data/*.json /app/data/

# Copy application properties
COPY src/main/resources/application.properties /app/application.properties

# Set environment variables for JSON file paths (optional, in case needed)
ENV USERS_FILE_PATH=/app/data/users.json
ENV PRODUCTS_FILE_PATH=/app/data/products.json
ENV CARTS_FILE_PATH=/app/data/carts.json
ENV ORDERS_FILE_PATH=/app/data/orders.json

# Expose application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app/mini1.jar"]
