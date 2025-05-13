# spring-grpc-eureka-load-balancing

This repository demonstrates a microservices architecture built with Spring gRPC, where the Order Service calls the Product Service while using Eureka for service discovery and load balancing. It uses Spring gRPC Starter for easy gRPC integration and Keycloak for securing the communication between services.

## Technologies Used
- Spring Boot
- Spring gRPC Starter
- Eureka Server & Eureka Client (for service discovery and load balancing)
- Keycloak (as a auth server for authentication)
- Gradle (build and dependency management)
- gRPC (communication protocol)

## Architecture Overview
- **Order Service** receives a JWT token from the user.
- Order Service calls the **Product Service** gRPC endpoint.
- The JWT token is forwarded from Order Service to Product Service using gRPC `CallCredentials`.
- Product Service validates the JWT token and returns the requested quantity and price information.
- Eureka handles service registration and client-side load balancing between multiple instances of Product Service.

## How It Works
1. The user authenticates and obtains a JWT token (via Keycloak).
2. The Order Service receives the incoming request with the JWT token.
3. When Order Service calls Product Service via gRPC, it attaches the JWT token in the call metadata using `CallCredentials`.
4. Product Service acts as a resource server and validates the token before processing the request.
5. Eureka manages service discovery and load balancing, allowing the Order Service to call one of the available Product Service instances seamlessly.

**Note:** In a real-world production setup, users would typically interact with an API Gateway, which performs the authentication with Keycloak and relays the JWT token to the Order Service, centralizing and securing the authentication flow.

## Getting Started

### Prerequisites
- Java 17 or higher
- Gradle
- Keycloak installed locally or from docker

### Running the Project

1. **Start Eureka Server**  
   Run the Eureka server (you can use the provided Gradle task or run it via Docker).

2. **Start Keycloak**  
   Run Keycloak server and configure your realm, client, and users as needed for JWT issuance.

3. **Start Product Service**  
   Build and run the Product Service application. It will register with Eureka and start listening to gRPC calls.

4. **Start Order Service**  
   Build and run the Order Service application. It will also register with Eureka.

5. **Make a Request**  
   Authenticate a user with Keycloak to get a JWT token.  
   Send a request to the Order Service including the JWT token.  
   Order Service will forward the token to Product Service via gRPC CallCredentials and fetch quantity and price.

## Notes
- This example emphasizes security in service-to-service communication using JWT tokens and CallCredentials.
- Eureka client load balancing is used to distribute requests across multiple Product Service instances.
- The project uses Gradle as the build tool.

