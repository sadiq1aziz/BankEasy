<img width="1687" height="922" alt="image" src="https://github.com/user-attachments/assets/b26e3f98-2a7e-48c4-9d69-3cb9115fbfb8" /># 🏦 BankEasy - Cloud-Native Microservices Banking Platform

> Enterprise-grade microservices platform demonstrating API Gateway, dual message brokers, distributed tracing, and comprehensive observability

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/Kafka-4.0-red)](https://kafka.apache.org/)
[![Kubernetes](https://img.shields.io/badge/Kubernetes-1.28+-blue)](https://kubernetes.io/)

---

## 📋 Table of Contents

- [Architecture Overview](#architecture-overview)
- [Key Highlights](#key-highlights)
- [Security and Authentication](#security-and-authentication)
- [Microservices](#microservices)
- [Communication Patterns](#communication-patterns)
- [Configuration Management](#configuration-management)
- [Observability](#observability)
- [Cloud Native Deployment](#cloud-native-deployment)
- [Technology Stack](#technology-stack)
- [GCP Deployment](#gcp-deployment) 


---

## 🏗️ Architecture Overview

<div align="center" style="background-color:#0d1117; padding:16px; border-radius:12px;">
  <a href="https://raw.githubusercontent.com/sadiq1aziz/BankEasy/c6a5e809ab01a04c39e2b9187518ee11e35f6d1c/assets/Bankeasy.svg" target="_blank" rel="noopener">
    <img src="https://raw.githubusercontent.com/sadiq1aziz/BankEasy/c6a5e809ab01a04c39e2b9187518ee11e35f6d1c/assets/Bankeasy.svg" 
         alt="BankEasy Architecture" 
         style="width:100%; max-width:1100px; height:auto; display:block; border-radius:8px; box-shadow:0 4px 12px rgba(0,0,0,0.4);">
  </a>
  <p style="color:#9aa7b0; font-size:12px; margin-top:8px;">Click the diagram to open full-resolution, zoomable SVG in a new tab.</p>
</div>

---
BankEasy follows a **cloud-native microservices architecture** designed for **scalability, resilience, and observability**. 
The platform consists of multiple independently deployable services orchestrated through **Kubernetes**, with centralized 
configuration management, dual messaging infrastructure, and comprehensive monitoring.

---

### ⚡ Key Highlights
- **Gateway Pattern**
  - Single entry point for all client requests.
  - Spring Cloud Gateway handles **routing, authentication, and cross-cutting concerns** like logging and rate-limiting.
- **Service Discovery**
  - Eureka-based service registry allows dynamic discovery of microservices.
  - Ensures services can scale or restart without manual reconfiguration.
- **Database per Service**
  - Each microservice maintains its **own MySQL database** for autonomy.
  - Prevents coupling and improves data encapsulation.
- **Event-Driven Communication**
  - Kafka provides **high-throughput asynchronous event streaming**.
  - RabbitMQ is used for **message queuing** in Spring Cloud Bus and inter-service notifications.
- **Distributed Tracing**
  - OpenTelemetry integration enables tracing requests across multiple services.
  - Tempo collects and visualizes spans for debugging performance issues.
- **Centralized Logging**
  - Loki aggregates logs from all services in structured JSON format.
  - Grafana provides a **visual dashboard** for log search, correlation, and monitoring.

---

### 🔐 Security and Authentication
- **OAuth2/OIDC Integration**
  - Keycloak manages authentication using OpenID Connect and OAuth2.
  - Microservices validate **JWT tokens** for stateless security.
- **API Gateway Security**
  - Gateway validates tokens before routing requests to downstream services.
  - Protects against unauthorized access at the entry point.
- **Resource Server Protection**
  - Each microservice is configured as an OAuth2 resource server.
  - Enforces **fine-grained access control** at the service level.
- **Role-Based Access Control**
  - Authorization is based on Keycloak roles.

---

### 🏢 Microservices 

## 1️⃣ Accounts Service
**Responsibility:** Core customer account management  
**Key Features:**
- Customer profile CRUD operations
- Account creation and management
- Integration with Cards and Loans services via OpenFeign
- Event publishing to Kafka for account lifecycle events
**Endpoints:**
- `POST /api/create` – Create new customer account
- `GET /api/fetch` – Retrieve account by mobile number
- `PUT /api/update` – Update account details
- `DELETE /api/delete` – Soft delete account
- `GET /api/customer-details` – Aggregate view with cards and loans
**Technologies:** Spring Boot, Spring Data JPA, MySQL, Redis Cache, OpenFeign

---

## 2️⃣ Cards Service
**Responsibility:** Credit/debit card lifecycle management  
**Key Features:**
- Card issuance
- Transaction tracking (amount used, outstanding balance)
- Card closure
- Async notifications via RabbitMQ to Message service
**Endpoints:**
- `POST /api/create` – Issue new card
- `GET /api/fetch` – Get card details by mobile number
- `PUT /api/update` – Update card limits or status
- `DELETE /api/delete` – Deactivate card
**Technologies:** Spring Boot, Spring Data JPA, MySQL, RabbitMQ, Resilience4j

---

## 3️⃣ Loans Service
**Responsibility:** Loan application
**Key Features:**
- Loan application processing
- Outstanding amount tracking
**Endpoints:**
- `POST /api/create` – Apply for new loan
- `GET /api/fetch` – Get loan details by mobile number
- `PUT /api/update` – Update loan status 
- `DELETE /api/delete` – Close loan account
**Technologies:** Spring Boot, Spring Data JPA, MySQL, Kafka (event publishing)

---

## 4️⃣ Message Service
**Responsibility:** Asynchronous notification delivery  
**Key Features:**
- SMS notifications via Twilio
- Email notifications via SMTP
- Queue-based message processing from RabbitMQ
- Event-driven notifications from Kafka topics
- Retry logic with dead-letter queue
- Notification status tracking
**Message Sources:**
- RabbitMQ queues for Cards/Loans notifications
- Kafka topics for Accounts events
- Direct API calls for immediate notifications
**Technologies:** Spring Boot, Spring Cloud Stream, RabbitMQ, Kafka, Twilio SDK, JavaMail

---

## 5️⃣ Config & Discovery Services

**Config Server:** Centralized configuration management for all microservices with profile-based configs and live refresh via Spring Cloud Bus.  

**Eureka Server:** Service discovery and registration for dynamic service location and health monitoring.  

**Gateway Server:** API Gateway providing a single entry point, JWT validation, rate limiting, circuit breakers, and route-based load balancing. 
    
---

### 🔄 Communication Patterns
- **Synchronous Communication**
  - REST APIs with **Spring Cloud OpenFeign** clients.
  - Enables direct inter-service requests with fallback strategies.
- **Asynchronous Communication**
  - Kafka handles **event streaming** between services.
  - RabbitMQ ensures **reliable message delivery** for critical workflows.
- **Rate Limiting**
  - Redis-backed rate limiting at the API gateway prevents overload.
- **Circuit Breaking**
  - Resilience4j implements **retry, timeout, and circuit breaker patterns**.
  - Ensures fault tolerance and graceful degradation of service.

---

### 📊 Configuration Management
- **Spring Cloud Config Server**
  - Centralized, Git-backed configuration repository.
  - Provides versioned configurations for all environments.
- **Dynamic Refresh**
  - Spring Cloud Bus with RabbitMQ propagates live config changes.
- **Environment Profiles**
  - Separate configurations for **dev, staging, and production**.

---

### 📈 Observability
- **Metrics**
  - Prometheus scrapes microservice metrics.
  - Grafana dashboards provide **real-time monitoring**.
- **Logging**
  - Structured JSON logs aggregated in Loki.
  - Labels allow **filtering by service, instance, or severity**.
- **Tracing**
  - OpenTelemetry spans exported to Tempo for distributed tracing.
  - Traces help identify **latency bottlenecks** across services.
- **Unified View**
  - Grafana correlates metrics, logs, and traces for **comprehensive debugging**.

---

### ☸️ Cloud Native Deployment 
- **Containerization**
  - All services packaged as Docker images with **multi-stage builds** for optimization.
- **Kubernetes Orchestration**
  - Helm charts for declarative deployments.
  - StatefulSets for stateful services (databases) and Deployments for stateless services.
- **Horizontal Scaling**
  - Kubernetes enables scaling individual services independently based on load.

---

### 🛠️ Technology Stack

**Backend Framework**
- Spring Boot 3.x with embedded server.
- Spring Cloud for microservices patterns: **Gateway, Config, Eureka, OpenFeign**.
- Spring Data JPA with Hibernate for ORM.
- Spring Security as OAuth2 resource server.
- Resilience4j for **circuit breaker, retry, and rate limiter patterns**.

**Messaging Infrastructure**
- Apache Kafka 4.0 for event streaming.
- RabbitMQ as AMQP broker for Spring Cloud Bus and queues.
- Spring Cloud Stream for messaging abstraction.

**Authentication & Authorization**
- Keycloak for identity management with OAuth2/OIDC.
- JWT for stateless authentication.
- Spring Security OAuth2 to protect endpoints.

**Data Layer**
- MySQL for each microservice (accounts, cards, loans).
- Redis for caching, rate limiting, and session management.
- Flyway/Liquibase for database migrations and version control.

**Observability & Monitoring**
- Prometheus for metrics collection and alerting.
- Grafana for visualization dashboards.
- Loki for centralized log aggregation.
- Tempo for distributed tracing.
- Grafana Alloy as unified telemetry collector.

**Container Orchestration**
- Docker with multi-stage builds.
- Kubernetes with StatefulSets and Deployments.
- Helm 3 for templated charts.
- Bitnami charts for production-ready MySQL, Kafka, Redis.

**CI/CD & DevOps**
- Docker Registry for storing container images.
- Kubernetes Secrets for secure credentials.

---

### ☸️ GCP Deployment
---
All app and resource images were deployed on Google Cloud Platform. Backend functionality validated via postman. 
Screenshots attached for reference. 
---

**KeyCloak setup via Ingress**

<img width="1619" height="758" alt="image" src="https://github.com/user-attachments/assets/0a71d4d2-7f54-4335-9f56-9efa423c56f8" />

<img width="975" height="299" alt="image" src="https://github.com/user-attachments/assets/1a8e44e1-b567-45cc-a271-72794556dc31" />

**WorkLoad Pods Check**

<img width="975" height="700" alt="image" src="https://github.com/user-attachments/assets/81b59216-07ac-4913-b247-6b86994e4957" />

<img width="975" height="573" alt="image" src="https://github.com/user-attachments/assets/c96588d8-a37a-4ae3-978a-cdefdf458539" />

**Postman Service Validation**

*Client Credential Token For OAuth*

<img width="945" height="564" alt="image" src="https://github.com/user-attachments/assets/68fb540d-2637-415c-8ac7-bd11384c7ee5" />

*Account creation Error Response*

<img width="975" height="560" alt="image" src="https://github.com/user-attachments/assets/e6a213ac-186c-4560-8918-d0aefb4bee97" />

*Account Created Sucessfully*

<img width="975" height="479" alt="image" src="https://github.com/user-attachments/assets/e97cfa1f-3eff-413f-b24c-b552fa982dc1" />

*Email Notification via MailTrap*

<img width="975" height="270" alt="image" src="https://github.com/user-attachments/assets/d344eb40-1ecb-4892-98a6-47674cdf79ed" />

*Account Deleted Successfully*

<img width="975" height="408" alt="image" src="https://github.com/user-attachments/assets/fe11ffa1-58be-4269-929f-1d1e9ca0f3fc" />

*Updated Account Successfully*

<img width="975" height="676" alt="image" src="https://github.com/user-attachments/assets/9e3cfca1-0d9d-43a5-9c0b-64b85090079d" />

*Fetch Account Successfully*

<img width="975" height="693" alt="image" src="https://github.com/user-attachments/assets/9ac5edeb-ce60-402c-906a-6158fb3cb7ce" />

*Create Card Successfully*

<img width="975" height="406" alt="image" src="https://github.com/user-attachments/assets/ad48074f-c8f7-47e4-8aea-46754ebd7c5e" />

*Email Notification via MailTrap*

<img width="975" height="265" alt="image" src="https://github.com/user-attachments/assets/643f3671-96e2-4a75-a9e3-3b73cbb4d0bf" />

*Card Deleted Successfully*

<img width="975" height="379" alt="image" src="https://github.com/user-attachments/assets/66b39750-bbaf-471e-a743-74ee721a89f8" />

*Card Updated Successfully*

<img width="975" height="536" alt="image" src="https://github.com/user-attachments/assets/b89e43bb-ec3e-474d-a582-31891650fa8a" />

*Card Fetched Successfully*

<img width="975" height="355" alt="image" src="https://github.com/user-attachments/assets/f6de10f1-d9a2-4455-831d-ecfba20d8498" />

*Loan Created Successfully*

<img width="975" height="489" alt="image" src="https://github.com/user-attachments/assets/a15e5fe2-7e73-4e12-9f61-cc0be32b89c8" />

*Loan Deleted Successfully*

<img width="975" height="408" alt="image" src="https://github.com/user-attachments/assets/8fc30e39-3107-4b00-a694-a3cb1136a25d" />

*Loan Updated Successfully*

<img width="975" height="507" alt="image" src="https://github.com/user-attachments/assets/90e09e07-f447-4065-ad6a-862ff63e561b" />

*Loan Fetched Successfully*

<img width="975" height="446" alt="image" src="https://github.com/user-attachments/assets/8bdf396e-8246-4238-9523-ad9b0f5addae" />

**Grafana - Health Metrics monitored via Prometheus**

<img width="975" height="532" alt="image" src="https://github.com/user-attachments/assets/2923d061-3ad9-48c2-a424-ecbb8030c037" />

**Grafana - Distributed tracing with Loki and Tempo**

<img width="975" height="533" alt="image" src="https://github.com/user-attachments/assets/a8d851d1-53f1-4449-8663-09008609cf9a" />


