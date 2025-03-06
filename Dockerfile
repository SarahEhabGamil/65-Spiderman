# Use OpenJDK as the base image
FROM openjdk:25-ea-4-jdk-oraclelinux9

WORKDIR /app

COPY target/mini1.jar /app/mini1.jar

COPY src/ /app/src/

ENV USERS_FILE_PATH=/app/src/main/java/com/example/data/users.json
ENV PRODUCTS_FILE_PATH=/app/src/main/java/com/example/data/products.json
ENV CARTS_FILE_PATH=/app/src/main/java/com/example/data/carts.json
ENV ORDERS_FILE_PATH=/app/src/main/java/com/example/data/orders.json

EXPOSE 8080

CMD ["java", "-jar", "/app/mini1.jar"]
