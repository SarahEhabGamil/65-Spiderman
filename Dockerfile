# Use OpenJDK as the base image
FROM openjdk:25-ea-4-jdk-oraclelinux9

WORKDIR /app

COPY target/mini1.jar /app/mini1.jar

COPY src/main/java/com/example/data/users.json /app/data/users.json
COPY src/main/java/com/example/data/products.json /app/data/products.json
COPY src/main/java/com/example/data/carts.json /app/data/carts.json
COPY src/main/java/com/example/data/orders.json /app/data/orders.json

EXPOSE 8080

CMD ["java", "-jar", "/app/mini1.jar"]
