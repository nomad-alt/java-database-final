# Retail Management System - Backend API

## Table of Contents

- [Overview](#overview)
- [API Documentation](#api-documentation)
  - [Base URL](#base-url)
  - [Endpoints](#endpoints)
- [Database Schema](#database-schema)
- [Exception Handling](#exception-handling)
- [Setup Instructions](#setup-instructions)
- [Testing](#testing)
- [License](#license)

## Overview

A comprehensive backend system for retail management built with:

- **Java 17**
- **Spring Boot 3.x**
- **Hibernate/JPA** (for MySQL)
- **MongoDB** (for unstructured data)
- **Maven** (build tool)

## API Documentation

### Base URL

http://localhost:8080

### Endpoints

#### Store Management (`/store`)

| Method | Endpoint              | Description              | Request Body      | Response           |
| ------ | --------------------- | ------------------------ | ----------------- | ------------------ |
| POST   | `/store`              | Add new store            | Store JSON        | Success message    |
| GET    | `/validate/{storeId}` | Validate store existence | -                 | Boolean            |
| POST   | `/placeOrder`         | Place new order          | OrderRequest JSON | Order confirmation |

#### Product Management (`/product`)

| Method | Endpoint        | Description       |
| ------ | --------------- | ----------------- |
| POST   | `/product`      | Add new product   |
| GET    | `/product/{id}` | Get product by ID |
| PUT    | `/product`      | Update product    |
| DELETE | `/product/{id}` | Delete product    |

#### Inventory Management (`/inventory`)

| Method | Endpoint     | Description         |
| ------ | ------------ | ------------------- |
| PUT    | `/inventory` | Update inventory    |
| GET    | `/{storeId}` | Get store inventory |

#### Review Management (`/reviews`)

| Method | Endpoint                 | Description         |
| ------ | ------------------------ | ------------------- |
| GET    | `/{storeId}/{productId}` | Get product reviews |

## Database Schema

### MySQL (Structured Data)

```sql
CREATE TABLE Store (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  address VARCHAR(255) NOT NULL
);

CREATE TABLE Product (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  category VARCHAR(50) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  sku VARCHAR(50) UNIQUE NOT NULL
);
```

MongoDB (Unstructured Data)
{
"customerId": 123,
"productId": 456,
"storeId": 789,
"rating": 5,
"comment": "Excellent product!"
}

Exception Handling
Error Response Format:
{
"timestamp": "2023-11-15T10:00:00.000+00:00",
"status": 400,
"error": "Bad Request",
"message": "Invalid input data",
"path": "/api/stores"
}

Common Status Codes:

400 Bad Request - Invalid input data

404 Not Found - Resource not found

500 Internal Server Error - Server-side error

Setup Instructions
Prerequisites:

Java 17 JDK

MySQL 8.0+

MongoDB 4.4+

Maven 3.8+

Configuration:

```bash
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/retail_db
spring.datasource.username=root
spring.datasource.password=secret

spring.data.mongodb.uri=mongodb://localhost:27017/retail_reviews
```

Run the application:

```bash
mvn spring-boot:run
```
