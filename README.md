# SikabethWalletAPI RESTful Web Service 
### A Wallet API Integrated With PayStack.

[![Java Build](https://img.shields.io/badge/Java-SpringBoot-brightgreen)](https://spring.io/projects/spring-boot) [![Spring JPA](https://img.shields.io/badge/Spring-JPA-blue)](https://spring.io/projects/spring-data-jpa) [![MongoDb](https://img.shields.io/badge/MongoDb-Passing-green)](https://www.postgresql.org/) [![Amazon Simple Email Service](https://img.shields.io/badge/Amazon-SES-orange)](https://aws.amazon.com/ses/) [![Swagger](https://img.shields.io/badge/Swagger-passing-green)](https://swagger.io/)  

This is a simple RESTful Web Service with Spring Boot - Demonstrating a wallet and a payment gateway.

## Features
* HTTP (POST, GET, PUT, DELETE) methods application in Rest Controller.
* Spring Security Implementation securing endpoints.
* Authentication and Authorization during login.
* Spring AOP with AspectJ for logging Advice to reduce crosscutting concern.
* Swagger Configuration.
* Amazon SES application during email verification and password reset request.

## Prerequisite
To build this project, you require:
- Endeavor to use your own ***accessKeyId*** && ***secretKey*** in AmazonSES class or comment AmazonSES usage.
- Then for PayStack implementation, you have to register with PayStack to get your secret_key to authorize your request.
- IntelliJ IDEA 2021.1.3 or above
- Maven 4.0.0 or above
- Spring 2.7.6

## Test it out
Clone

## Libraries
*   [Spring Boot Web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
*   [Jackson dataformat](https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml)
*   [Spring JPA](https://spring.io/projects/spring-data-jpa)
*   [MongoDb](https://mongodb.com/cloud/atlas/register)
*   [Swagger](https://swagger.io/)
*   [Amazon SES](https://aws.amazon.com/ses/)


## Other projects
https://github.com/ikechiU?tab=repositories

## Author
Ikechi Ucheagwu 