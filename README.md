

#  AI-Powered Fitness Recommendation System

A full-stack, microservices-based fitness application that provides personalized fitness recommendations using an AI-powered API.
The system is built with **Spring Boot microservices**, secured using **OAuth 2.0 (Keycloak)**, supports asynchronous communication via **RabbitMQ**, and uses **React.js** for the frontend.

---

##  Architecture Overview

The application follows a microservices architecture consisting of:

* Eureka Server – Service discovery
* Spring Cloud Gateway – Centralized API gateway and routing
* Keycloak – OAuth2-based authentication and authorization
* Fitness Services – Core business logic
* Recommendation Service – AI-powered recommendations
* RabbitMQ – Asynchronous, event-driven communication
* PostgreSQL – Persistent data storage
* React.js Frontend – User interface

---

##  Tech Stack

### Backend

* Java
* Spring Boot
* Spring Cloud (Eureka, Gateway)
* OAuth 2.0 / JWT (Keycloak)
* RabbitMQ
* PostgreSQL

### Frontend

* React (Vite)
* Material UI
* Redux Toolkit
* Axios
* React Router

### DevOps

* Docker
* Docker Compose

---

## Features

* Secure OAuth2 login with Keycloak
* JWT-based authentication and authorization
* Centralized API routing via Spring Cloud Gateway
* Service discovery using Eureka Server
* Asynchronous messaging with RabbitMQ
* AI-powered fitness recommendations
* Responsive frontend built with React and Material UI

---

##  Project Structure

```
fitness-app/
├── eureka-server/
├── api-gateway/
├── auth-service/
├── fitness-service/
├── recommendation-service/
├── frontend/
├── docker-compose.yml
└── README.md
```

---

##  Prerequisites

Ensure the following are installed:

* Java 17+
* Node.js 18+
* Docker
* Docker Compose

---

## Infrastructure & Application Setup (Docker Compose)

All infrastructure and application services are managed using **Docker Compose**, including:

* Keycloak
* RabbitMQ
* PostgreSQL
* Eureka Server
* Spring Cloud Gateway
* Backend microservices
* React frontend

---

## Running the Application

From the project root, start all services using:

```
docker-compose up -d
```

To stop all services:

```
docker-compose down
```

To stop and remove volumes:

```
docker-compose down -v
```

---

## Keycloak (Authentication Service)

Keycloak is used for OAuth2-based authentication and authorization and is provisioned via Docker Compose.

**Access Keycloak Admin Console:**

```
http://localhost:8181
```

Default credentials (development only):

* Username: `admin`
* Password: `admin`

> Note: Secrets and credentials should be externalized for production environments.

---

##  RabbitMQ (Messaging Service)

RabbitMQ is used for asynchronous, event-driven communication between microservices.

**RabbitMQ Management Console:**

```
http://localhost:15672
```

Default credentials:

* Username: `guest`
* Password: `guest`

---

## Application Startup Order

Docker Compose ensures services are started correctly.
Logically, the flow is:

1. Keycloak
2. RabbitMQ
3. Eureka Server
4. Spring Cloud Gateway
5. Backend Microservices
6. React Frontend

---

## Authentication Flow

1. User logs in via Keycloak
2. Keycloak issues JWT tokens
3. Frontend sends JWT to API Gateway
4. Gateway routes request to the appropriate microservice
5. Services communicate asynchronously via RabbitMQ
6. Recommendation service invokes the AI API
7. Personalized data is returned to the frontend

---

##  Service URLs (Verification)

* Eureka Dashboard: [http://localhost:8761](http://localhost:8761)
* API Gateway: [http://localhost:8080](http://localhost:8080)
* Frontend: [http://localhost:5173](http://localhost:5173)
* Keycloak: [http://localhost:8181](http://localhost:8181)
* RabbitMQ: [http://localhost:15672](http://localhost:15672)

---

##  Notes

* Docker Compose is the single source of truth for service configuration
* `start-dev` mode is used for Keycloak in local development
* Ensure environment variables are used for secrets in production
* Gateway routes are dynamically discovered via Eureka

---

##  Author

**Keerthana**

---

##  License

This project is intended for learning and development purposes.


