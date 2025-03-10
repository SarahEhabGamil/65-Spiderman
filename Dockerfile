FROM openjdk:23-jdk-slim
WORKDIR /app

RUN apt update && apt install -y maven

COPY . /app

RUN mkdir -p /app/data

ENV USERS_FILE_PATH=/app/data/users.json
ENV PRODUCTS_FILE_PATH=/app/data/products.json
ENV CARTS_FILE_PATH=/app/data/carts.json
ENV ORDERS_FILE_PATH=/app/data/orders.json

EXPOSE 8080

# run only
#CMD ["java", "-jar", "/app/target/mini1.jar"]

 #test before run
CMD ["sh", "-c", "mvn test && mvn clean package && java -jar /app/target/mini1.jar"]