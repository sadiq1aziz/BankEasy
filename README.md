# 🏦 BankEasy - Cloud-Native Microservices Banking Platform

> Enterprise-grade microservices platform demonstrating API Gateway, dual message brokers, distributed tracing, and comprehensive observability

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/Kafka-4.0-red)](https://kafka.apache.org/)
[![Kubernetes](https://img.shields.io/badge/Kubernetes-1.28+-blue)](https://kubernetes.io/)

---

## 📋 Table of Contents

- [Architecture Overview](#architecture-overview)
- [Key Features](#key-features)
- [Technology Stack](#technology-stack)
- [Quick Start](#quick-start)
- [Microservices](#microservices)
- [Communication Patterns](#communication-patterns)
- [Observability](#observability)
- [Lessons Learned](#lessons-learned)
- [API Documentation](#api-documentation)

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

### ⚡ Architecture Highlights
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

### 🔐 Security & Authentication
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
  - Supports **user-specific permissions and admin privileges**.

---

### 🚀 Microservices Architecture
- **Accounts Service**
  - Manages customer accounts.
  - Handles **CRUD operations** and maintains account metadata.
- **Cards Service**
  - Lifecycle management of credit/debit cards.
  - Processes **transactions, card issuance, and block/unblock actions**.
- **Loans Service**
  - Manages loan applications and repayment tracking.
  - Calculates interest and schedules payment plans.
- **Message Service**
  - Sends notifications asynchronously via **Twilio (SMS) and SMTP (Email)**.
  - Supports **event-driven alerts** for account and loan activities.

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
- **Encryption Support**
  - Sensitive properties encrypted using symmetric/asymmetric keys.

---

### 📈 Observability Stack
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

### ☸️ Cloud-Native Deployment
- **Containerization**
  - All services packaged as Docker images with **multi-stage builds** for optimization.
- **Kubernetes Orchestration**
  - Helm charts for declarative deployments.
  - StatefulSets for stateful services (databases) and Deployments for stateless services.
- **Service Mesh Ready**
  - Supports Istio/Linkerd integration for traffic management and observability.
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
- 

## 🏢 Microservices Overview

### 1️⃣ Accounts Service
**Responsibility:** Core customer account management  
**Key Features:**
- Customer profile CRUD operations
- Account creation and management
- Balance tracking and transaction history
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

### 2️⃣ Cards Service
**Responsibility:** Credit/debit card lifecycle management  
**Key Features:**
- Card issuance and activation
- Credit limit management
- Transaction tracking (amount used, outstanding balance)
- Card renewal and closure workflows
- Async notifications via RabbitMQ to Message service
**Endpoints:**
- `POST /api/create` – Issue new card
- `GET /api/fetch` – Get card details by mobile number
- `PUT /api/update` – Update card limits or status
- `DELETE /api/delete` – Deactivate card
**Technologies:** Spring Boot, Spring Data JPA, MySQL, RabbitMQ, Resilience4j

---

### 3️⃣ Loans Service
**Responsibility:** Loan application and repayment tracking  
**Key Features:**
- Loan application processing
- Principal and interest calculation
- Repayment schedule management
- Outstanding amount tracking
- Loan status updates (pending, approved, rejected, closed)
**Endpoints:**
- `POST /api/create` – Apply for new loan
- `GET /api/fetch` – Get loan details by mobile number
- `PUT /api/update` – Update loan status or repayment
- `DELETE /api/delete` – Close loan account
**Technologies:** Spring Boot, Spring Data JPA, MySQL, Kafka (event publishing)

---

### 4️⃣ Message Service
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

### 5️⃣ Config & Discovery Services

**Config Server:** Centralized configuration management for all microservices with profile-based configs and live refresh via Spring Cloud Bus.  

**Eureka Server:** Service discovery and registration for dynamic service location and health monitoring.  

**Gateway Server:** API Gateway providing a single entry point, JWT validation, rate limiting, circuit breakers, and route-based load balancing.  

**CI/CD & DevOps**
- GitHub Actions for automated testing, building, and deployment.
- Jenkins as alternative CI/CD solution.
- Docker Registry for storing container images.
- Kubernetes Secrets for secure credentials.
