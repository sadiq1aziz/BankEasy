# BankEasy - Cloud-Native Microservices Banking Platform

> Enterprise-grade microservices platform demonstrating API Gateway, dual message brokers, distributed tracing, and comprehensive observability

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/Kafka-4.0-red)](https://kafka.apache.org/)
[![Kubernetes](https://img.shields.io/badge/Kubernetes-1.28+-blue)](https://kubernetes.io/)

## 📋 Table of Contents

- [Architecture Overview](#architecture-overview)
- [Key Features](#key-features)
- [Technology Stack](#technology-stack)
- [Quick Start](#quick-start)
- [Services](#services)
- [Communication Patterns](#communication-patterns)
- [Observability](#observability)
- [Lessons Learned](#lessons-learned)
- [API Documentation](#api-documentation)

## 🏗️ Architecture Overview
```mermaid
graph TB
    subgraph client["Client Layer"]
        web["Web / Mobile Client"]
    end

    subgraph k8s["Kubernetes Cluster"]
        subgraph services["Microservices"]
            gw["Gateway :8072"]
            accounts["Accounts :8080"]
            cards["Cards :9000"]
            loans["Loans :8090"]
            message["Message :9010"]
        end

        subgraph infra["Service Infrastructure"]
            eureka["Eureka Server :8761"]
            config["Config Server :8071\nGit-backed"]
            bus["Spring Cloud Bus\n(Kafka / RabbitMQ)"]
        end

        subgraph data["Data Layer"]
            mysql["MySQL"]
            kafka["Kafka Cluster"]
            rabbitmq["RabbitMQ"]
            redis["Redis Cache"]
        end

        subgraph obs["Observability Stack"]
            grafana["Grafana"]
            prometheus["Prometheus"]
            loki["Loki (Logs)"]
            tempo["Tempo (Tracing)"]
        end
    end

    %% Client interactions
    web --> gw

    %% Service routing via API Gateway
    gw --> accounts & cards & loans

    %% Service Discovery
    accounts -.registers.-> eureka
    cards -.registers.-> eureka
    loans -.registers.-> eureka
    message -.registers.-> eureka

    %% Config propagation
    config -.reads configs from Git Repo.-> git["Config Git Repo"]
    config -.POST /busrefresh.-> bus
    bus -.broadcast refresh event.-> accounts & cards & loans & message

    %% Service-to-service calls
    accounts -.Feign.-> cards & loans

    %% Async communication
    cards --> kafka --> message
    accounts --> rabbitmq --> message

    %% External notifications
    message --> external["Email / SMS via Twilio"]

    %% Persistence & cache
    accounts --> mysql
    cards --> mysql
    loans --> mysql
    message --> mysql
    accounts --> redis
    cards --> redis
    loans --> redis

    %% Observability connections
    accounts --> prometheus
    cards --> prometheus
    loans --> prometheus
    message --> prometheus
    prometheus --> grafana
    loki --> grafana
    tempo --> grafana

    %% Styling
    style kafka fill:#ff9999,stroke:#cc0000,stroke-width:1px
    style rabbitmq fill:#ff9933,stroke:#cc6600,stroke-width:1px
    style mysql fill:#4da6ff,stroke:#0066cc,stroke-width:1px
    style redis fill:#e06666,stroke:#b22222,stroke-width:1px
    style bus fill:#ccffcc,stroke:#009933,stroke-width:1px
    style grafana fill:#ffd966,stroke:#cc9900,stroke-width:1px
