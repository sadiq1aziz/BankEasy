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
    %% ==== CLIENT LAYER ====
    subgraph Client["Client Layer"]
        Web[Web / Mobile Client]
    end

    %% ==== KUBERNETES CLUSTER ====
    subgraph K8s["Kubernetes Cluster"]
        
        %% ==== INFRASTRUCTURE ====
        subgraph Infra["Infrastructure"]
            Eureka[Eureka Server :8761]
            Config[Config Server :8071 (Git-backed)]
        end

        %% ==== SERVICES ====
        subgraph Services["Microservices"]
            Gateway[API Gateway :8072]
            Accounts[Accounts Service :8080]
            Cards[Cards Service :9000]
            Loans[Loans Service :8090]
            Message[Message Service :9010]
        end

        %% ==== DATA LAYER ====
        subgraph Data["Data Layer"]
            MySQL[(MySQL Databases)]
            Kafka[(Kafka Cluster ×3 Brokers)]
            RabbitMQ[(RabbitMQ Queue)]
            Redis[(Redis Cache)]
        end

        %% ==== OBSERVABILITY ====
        subgraph Obs["Observability"]
            Grafana[(Grafana Stack)]
        end
    end

    %% ==== FLOWS ====
    Web --> Gateway
    Gateway --> Accounts
    Gateway --> Cards
    Gateway --> Loans

    Accounts -. Feign Client .-> Cards
    Accounts -. Feign Client .-> Loans

    Cards --> Kafka --> Message
    Accounts --> RabbitMQ --> Message
    Message --> External[External Email/SMS APIs]

    %% ==== COLOR STYLING ====
    style Kafka fill:#f8d7da,stroke:#b30000
    style RabbitMQ fill:#f6b26b,stroke:#cc6600
    style MySQL fill:#a4c2f4,stroke:#1155cc
    style Redis fill:#e06666,stroke:#990000
    style Grafana fill:#ffe599,stroke:#b8860b
    style Config fill:#c9daf8,stroke:#1155cc
    style Eureka fill:#d9ead3,stroke:#38761d
    style Gateway fill:#d5a6bd,stroke:#741b47
    style Accounts fill:#b4a7d6,stroke:#351c75
    style Cards fill:#b4a7d6,stroke:#351c75
    style Loans fill:#b4a7d6,stroke:#351c75
    style Message fill:#ead1dc,stroke:#a61c00
