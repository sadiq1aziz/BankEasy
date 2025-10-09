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

BankEasy follows a cloud-native microservices architecture designed for scalability, resilience, and observability. The platform consists of multiple independently deployable services orchestrated through Kubernetes, with centralized configuration management, dual messaging infrastructure, and comprehensive monitoring.
Architecture Highlights

Gateway Pattern: Single entry point with Spring Cloud Gateway handling routing, authentication, and cross-cutting concerns
Service Discovery: Eureka-based service registry for dynamic service location
Database per Service: Each microservice maintains its own MySQL database ensuring data autonomy
Event-Driven Communication: Kafka for event streaming and RabbitMQ for message queuing
Distributed Tracing: OpenTelemetry integration with Tempo for request tracing across services
Centralized Logging: Loki aggregates logs from all services with Grafana visualization


Key Features
🔐 Security & Authentication

OAuth2/OIDC Integration: Keycloak-based authentication with JWT token validation
API Gateway Security: Token validation at gateway level before routing to downstream services
Resource Server Protection: Each microservice configured as OAuth2 resource server
Role-Based Access Control: Fine-grained authorization based on Keycloak roles

🚀 Microservices Architecture

Accounts Service: Core customer account management with CRUD operations
Cards Service: Credit/debit card lifecycle management and transaction handling
Loans Service: Loan application processing and repayment tracking
Message Service: Asynchronous notification delivery (SMS/Email) via Twilio and SMTP

🔄 Communication Patterns

Synchronous: REST APIs with OpenFeign client for inter-service communication
Asynchronous: Kafka for event streaming and RabbitMQ for reliable message queuing
Rate Limiting: Redis-backed rate limiting at gateway level
Circuit Breaking: Resilience4j patterns for fault tolerance

📊 Configuration Management

Spring Cloud Config Server: Git-backed centralized configuration repository
Dynamic Refresh: Spring Cloud Bus with RabbitMQ for live configuration updates
Environment Profiles: Separate configurations for dev, staging, and production
Encryption Support: Sensitive data encrypted using symmetric/asymmetric keys

📈 Observability Stack

Metrics: Prometheus scraping with Grafana dashboards for visualization
Logging: Structured JSON logs aggregated in Loki
Tracing: OpenTelemetry spans exported to Tempo
Unified View: Grafana correlates metrics, logs, and traces for comprehensive debugging

☸️ Cloud-Native Deployment

Containerization: Docker images for all services with multi-stage builds
Kubernetes Orchestration: Helm charts for declarative infrastructure
Service Mesh Ready: Architecture supports Istio/Linkerd integration
Horizontal Scaling: StatefulSets for stateful services, Deployments for stateless


Technology Stack
Backend Framework

Spring Boot 3.x: Core application framework with embedded server
Spring Cloud: Microservices patterns (Gateway, Config, Eureka, OpenFeign)
Spring Data JPA: ORM with Hibernate for database operations
Spring Security: OAuth2 resource server implementation
Resilience4j: Circuit breaker, retry, and rate limiter patterns

Messaging Infrastructure

Apache Kafka 4.0: Event streaming platform for high-throughput async communication
RabbitMQ: AMQP message broker for Spring Cloud Bus and inter-service queues
Spring Cloud Stream: Abstraction layer for messaging binders

Authentication & Authorization

Keycloak: Identity and access management with OIDC/OAuth2 protocols
JWT: Stateless token-based authentication
Spring Security OAuth2: Resource server configuration for protected endpoints

Data Layer

MySQL: Relational database for each microservice (accounts, cards, loans)
Redis: In-memory cache for rate limiting and session management
Flyway/Liquibase: Database migration and version control

Observability & Monitoring

Prometheus: Time-series metrics collection and alerting
Grafana: Visualization dashboards for metrics, logs, and traces
Loki: Log aggregation with label-based indexing
Tempo: Distributed tracing backend for OpenTelemetry spans
Grafana Alloy: OpenTelemetry collector for unified telemetry pipeline

Container Orchestration

Docker: Containerization with multi-stage builds for optimized images
Kubernetes: Container orchestration with StatefulSets and Deployments
Helm 3: Package manager for Kubernetes with templated charts
Bitnami Charts: Production-ready charts for MySQL, Kafka, Redis

CI/CD & DevOps

GitHub Actions: Automated testing, building, and deployment pipelines
Jenkins: Alternative CI/CD with declarative pipelines
Docker Registry: Private registry for container images
Kubernetes Secrets: Secure credential management
