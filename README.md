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

## Key Features

- Microservices architecture with **Accounts, Cards, Loans, Message** services  
- **API Gateway** with OAuth2 token validation (Keycloak), rate limiting, and circuit breakers  
- Dual message brokers: **Kafka** (event streaming) and **RabbitMQ** (config bus + inter-service queues)  
- Centralized configuration via **Spring Cloud Config** (Git-backed) + **Spring Cloud Bus** for live refresh  
- Full observability: **Prometheus**, **Grafana**, **Loki**, **Tempo** and **Alloy**  
- Per-service SQL databases (MySQL) and **Redis** for caching / rate-limiting  
- Kubernetes-ready (Helm charts), CI/CD friendly (GitHub Actions / Jenkins)

---

## Technology Stack

**Backend:** Spring Boot, Spring Cloud, Feign  
**Messaging:** Kafka, RabbitMQ  
**Auth:** Keycloak (OIDC / OAuth2)  
**Datastore:** MySQL, Redis  
**Observability:** Prometheus, Grafana, Loki, Tempo, Alloy (OTel)  
**Deployment:** Docker, Helm, Kubernetes  
**CI/CD:** GitHub Actions, Jenkins

---
