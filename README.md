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
---
config:
  layout: elk
---
flowchart TB
 subgraph client["Client Layer"]
        web["Web Client/Postman/Mobile"]
  end
 subgraph gateway["API Gateway Layer"]
        gw["Gateway Server<br>Port: 8072<br>Spring Cloud Gateway<br>Circuit Breaker + Rate Limiting"]
  end
 subgraph services["Business Microservices"]
        accounts["Accounts Service<br>Port: 8080<br>Orchestrator + Feign Clients"]
        cards["Cards Service<br>Port: 9000<br>Kafka Producer"]
        loans["Loans Service<br>Port: 8090<br>Domain Service"]
        message["Message Service<br>Port: 9010<br>Dual Broker Consumer"]
  end
 subgraph infrastructure["Infrastructure Services"]
        eureka["Eureka Server<br>Port: 8761<br>Service Discovery"]
        config["Config Server<br>Port: 8071<br>Git-backed Config<br>RabbitMQ Bus"]
  end
 subgraph messaging["Message Brokers"]
        rabbitmq["RabbitMQ<br>Port: 5672<br>Config Bus + Accounts"]
        kafka0["Kafka Broker 0"]
        kafka1["Kafka Broker 1"]
        kafka2["Kafka Broker 2"]
  end
 subgraph data["Data & Cache"]
        mysql[("MySQL 8.0<br>Port: 3306<br>accountsdb")]
        redis[("Redis<br>Rate Limiter")]
  end
 subgraph observability["Observability Stack"]
        prometheus["Prometheus<br>Metrics Collection"]
        grafana["Grafana<br>Unified Dashboard"]
        loki["Loki<br>Log Aggregation"]
        tempo["Tempo<br>Distributed Tracing"]
        alloy["Alloy<br>Telemetry Agent"]
  end
 subgraph k8s["Kubernetes Cluster (Helm Deployed)"]
        gateway
        services
        infrastructure
        messaging
        data
        observability
  end
 subgraph external["External Systems"]
        github["GitHub<br>bankeasy-config repo"]
        mailtrap["Mailtrap<br>Email Service"]
        twilio["Twilio<br>SMS Service"]
  end
    web -- HTTP/REST --> gw
    gw -- /bankeasy/accounts/**<br>Circuit Breaker 4s --> accounts
    gw -- /bankeasy/cards/**<br>Rate Limit 1/s --> cards
    gw -- /bankeasy/loans/**<br>Retry 3x --> loans
    accounts -. Feign: Customer 360° .-> cards & loans
    gw -- register/discover --> eureka
    accounts -- register/discover --> eureka
    cards -- register/discover --> eureka
    loans -- register/discover --> eureka
    message -- register/discover --> eureka
    gw --> config
    accounts --> config & mysql
    cards --> config
    loans --> config
    message --> config
    config -- "clone-on-start" --> github
    config -- /busrefresh actuator --> rabbitmq
    rabbitmq -. config change event .-> accounts & cards & loans & message & gw
    gw -. check limit .-> redis
    cards -.-> mysql
    loans -.-> mysql
    accounts -- "send-notification queue<br>AccountsMessageDto" --> rabbitmq
    rabbitmq --> message
    message -- "update-notification exchange" --> rabbitmq
    cards -- "send-card-notification-topic<br>CardsMessageDto" --> kafka0
    kafka0 -. "replication RF=3" .-> kafka1
    kafka1 -. "replication RF=3" .-> kafka2
    kafka2 -. "replication RF=3" .-> kafka0
    kafka0 -- consumer group: message --> message
    message -- "update-card-notification-topic" --> kafka0
    message -- SMTP --> mailtrap
    message -- SMS API --> twilio
    alloy -- scrape /actuator/prometheus --> accounts & cards & loans & message & gw
    alloy --> prometheus & loki
    prometheus --> grafana
    accounts -. structured logs .-> alloy
    cards -. structured logs .-> alloy
    loans -. structured logs .-> alloy
    message -. structured logs .-> alloy
    gw -. structured logs .-> alloy
    loki --> grafana
    accounts -. OTel traces .-> tempo
    cards -. OTel traces .-> tempo
    loans -. OTel traces .-> tempo
    message -. OTel traces .-> tempo
    gw -. OTel traces .-> tempo
    tempo --> grafana
    style gw fill:#cc99ff,stroke:#333,stroke-width:2px
    style eureka fill:#99ff99,stroke:#333,stroke-width:2px
    style config fill:#ffcc99,stroke:#333,stroke-width:2px
    style rabbitmq fill:#ff6600,stroke:#333,stroke-width:2px
    style kafka0 fill:#ff9999,stroke:#333,stroke-width:2px
    style kafka1 fill:#ff9999,stroke:#333,stroke-width:2px
    style kafka2 fill:#ff9999,stroke:#333,stroke-width:2px
    style mysql fill:#4da6ff,stroke:#333,stroke-width:2px
    style redis fill:#ffcc00,stroke:#333,stroke-width:2px
    style prometheus fill:#ff6699,stroke:#333,stroke-width:2px
    style grafana fill:#ff6699,stroke:#333,stroke-width:2px
    style loki fill:#ff6699,stroke:#333,stroke-width:2px
    style tempo fill:#ff6699,stroke:#333,stroke-width:2px
