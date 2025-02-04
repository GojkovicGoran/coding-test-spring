# coding-test-spring
A Spring Boot application for synchronizing products from PIM (Product Information Management) to Shopify.

## Configuration
Configure properties in `application.properties` or `compose.yaml` with 
Shopify Admin API access tokens for both stores:

shopify.pim.store-name=???????
shopify.pim.access-token=???????
shopify.receiver.store-name=???????
shopify.receiver.access-token=??????????

## Prerequisites
- Java 21
- Maven
- MySQL (for production)
- H2 (for development/testing)

## Installation
```bash
git clone [repository-url]

```bash
mvn clean install

```bash
mvn spring-boot:run

## Server Port
server.port=8081

Once the application is running, you can access atwith SHOPIFY_ACCESS_TOKEN:

http://localhost:8081/api/sync/products  

## Run unit tests
```bash
mvn test

## Features

- Product synchronization between PIM and Shopify
- RESTful API endpoints
- Data validation and error handling
- Database integration with MySQL/H2
- Swagger API documentation
- MapStruct for object mapping
- Comprehensive test coverage

## Technology Stack

- Spring Boot 3.4.2
- Spring Data JPA
- MapStruct 1.5.5.Final
- Lombok
- JUnit 5 with Testcontainers

## Project Structure
src
├── main
│   ├── java
│   │   └── com.ggcode.ggshopify
│   │       ├── config
│   │       ├── controller
│   │       ├── model
│   │       ├── repository
│   │       ├── service
│   │       └── mapper
│   └── resources
│       └── application.properties
└── test
    └── java
        └── com.ggcode.ggshopify
