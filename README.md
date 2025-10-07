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
        web[Web/Mobile Client]
    end
    
    subgraph k8s["Kubernetes Cluster"]
        subgraph services["Microservices"]
            gw[Gateway<br/>8072]
            accounts[Accounts<br/>8080]
            cards[Cards<br/>9000]
            loans[Loans<br/>8090]
            message[Message<br/>9010]
        end
        
        subgraph infra["Infrastructure"]
            eureka[Eureka<br/>8761]
            config[Config<br/>8071]
        end
        
        subgraph data["Data Layer"]
            mysql[(MySQL)]
            kafka[Kafka x3]
            rabbitmq[RabbitMQ]
            redis[(Redis)]
        end
        
        subgraph obs["Observability"]
            grafana[Grafana Stack]
        end
    end
    
    web --> gw
    gw --> accounts & cards & loans
    accounts -.Feign.-> cards & loans
    cards --> kafka --> message
    accounts --> rabbitmq --> message
    message --> external[Email/SMS]
    
    style kafka fill:#ff9999
    style rabbitmq fill:#ff6600
    style mysql fill:#4da6ff
