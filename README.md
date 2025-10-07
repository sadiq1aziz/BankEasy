graph TB
    subgraph client["Client Layer"]
        web[Web Client/Postman/Mobile]
    end
    
    subgraph k8s["Kubernetes Cluster (Helm Deployed)"]
        subgraph gateway["API Gateway Layer"]
            gw[Gateway Server<br/>Port: 8072<br/>Spring Cloud Gateway<br/>Circuit Breaker + Rate Limiting]
        end
        
        subgraph services["Business Microservices"]
            accounts[Accounts Service<br/>Port: 8080<br/>Orchestrator + Feign Clients]
            cards[Cards Service<br/>Port: 9000<br/>Kafka Producer]
            loans[Loans Service<br/>Port: 8090<br/>Domain Service]
            message[Message Service<br/>Port: 9010<br/>Dual Broker Consumer]
        end
        
        subgraph infrastructure["Infrastructure Services"]
            eureka[Eureka Server<br/>Port: 8761<br/>Service Discovery]
            config[Config Server<br/>Port: 8071<br/>Git-backed Config<br/>RabbitMQ Bus]
        end
        
        subgraph messaging["Message Brokers"]
            rabbitmq[RabbitMQ<br/>Port: 5672<br/>Config Bus + Accounts]
            kafka0[Kafka Broker 0]
            kafka1[Kafka Broker 1]
            kafka2[Kafka Broker 2]
        end
        
        subgraph data["Data & Cache"]
            mysql[(MySQL 8.0<br/>Port: 3306<br/>accountsdb)]
            redis[(Redis<br/>Rate Limiter)]
        end
        
        subgraph observability["Observability Stack"]
            prometheus[Prometheus<br/>Metrics Collection]
            grafana[Grafana<br/>Unified Dashboard]
            loki[Loki<br/>Log Aggregation]
            tempo[Tempo<br/>Distributed Tracing]
            alloy[Alloy<br/>Telemetry Agent]
        end
    end
    
    subgraph external["External Systems"]
        github[GitHub<br/>bankeasy-config repo]
        mailtrap[Mailtrap<br/>Email Service]
        twilio[Twilio<br/>SMS Service]
    end
    
    %% Client Flow
    web -->|HTTP/REST| gw
    
    %% Gateway Routing with Resilience
    gw -->|/bankeasy/accounts/**<br/>Circuit Breaker 4s| accounts
    gw -->|/bankeasy/cards/**<br/>Rate Limit 1/s| cards
    gw -->|/bankeasy/loans/**<br/>Retry 3x| loans
    
    %% Feign Client Calls (Synchronous)
    accounts -.->|Feign: Customer 360°| cards
    accounts -.->|Feign: Customer 360°| loans
    
    %% Service Discovery (Eureka)
    gw -->|register/discover| eureka
    accounts -->|register/discover| eureka
    cards -->|register/discover| eureka
    loans -->|register/discover| eureka
    message -->|register/discover| eureka
    
    %% Configuration Management
    gw --> config
    accounts --> config
    cards --> config
    loans --> config
    message --> config
    config -->|clone-on-start| github
    
    %% Spring Cloud Bus (Config Refresh)
    config -->|/busrefresh actuator| rabbitmq
    rabbitmq -.->|config change event| accounts
    rabbitmq -.->|config change event| cards
    rabbitmq -.->|config change event| loans
    rabbitmq -.->|config change event| message
    rabbitmq -.->|config change event| gw
    
    %% Rate Limiting
    gw -.->|check limit| redis
    
    %% Database Access
    accounts --> mysql
    cards -.-> mysql
    loans -.-> mysql
    
    %% RabbitMQ Messaging (Accounts Flow)
    accounts -->|send-notification queue<br/>AccountsMessageDto| rabbitmq
    rabbitmq --> message
    message -->|update-notification exchange| rabbitmq
    
    %% Kafka Messaging (Cards Flow)
    cards -->|send-card-notification-topic<br/>CardsMessageDto| kafka0
    kafka0 -.->|replication RF=3| kafka1
    kafka1 -.->|replication RF=3| kafka2
    kafka2 -.->|replication RF=3| kafka0
    kafka0 -->|consumer group: message| message
    message -->|update-card-notification-topic| kafka0
    
    %% External Notifications
    message -->|SMTP| mailtrap
    message -->|SMS API| twilio
    
    %% Observability - Metrics
    alloy -->|scrape /actuator/prometheus| accounts
    alloy -->|scrape /actuator/prometheus| cards
    alloy -->|scrape /actuator/prometheus| loans
    alloy -->|scrape /actuator/prometheus| message
    alloy -->|scrape /actuator/prometheus| gw
    alloy --> prometheus
    prometheus --> grafana
    
    %% Observability - Logs
    accounts -.->|structured logs| alloy
    cards -.->|structured logs| alloy
    loans -.->|structured logs| alloy
    message -.->|structured logs| alloy
    gw -.->|structured logs| alloy
    alloy --> loki
    loki --> grafana
    
    %% Observability - Traces
    accounts -.->|OTel traces| tempo
    cards -.->|OTel traces| tempo
    loans -.->|OTel traces| tempo
    message -.->|OTel traces| tempo
    gw -.->|OTel traces| tempo
    tempo --> grafana
    
    %% Styling
    style kafka0 fill:#ff9999,stroke:#333,stroke-width:2px
    style kafka1 fill:#ff9999,stroke:#333,stroke-width:2px
    style kafka2 fill:#ff9999,stroke:#333,stroke-width:2px
    style rabbitmq fill:#ff6600,stroke:#333,stroke-width:2px
    style mysql fill:#4da6ff,stroke:#333,stroke-width:2px
    style redis fill:#ffcc00,stroke:#333,stroke-width:2px
    style eureka fill:#99ff99,stroke:#333,stroke-width:2px
    style config fill:#ffcc99,stroke:#333,stroke-width:2px
    style gw fill:#cc99ff,stroke:#333,stroke-width:2px
    style grafana fill:#ff6699,stroke:#333,stroke-width:2px
    style prometheus fill:#ff6699,stroke:#333,stroke-width:2px
    style loki fill:#ff6699,stroke:#333,stroke-width:2px
    style tempo fill:#ff6699,stroke:#333,stroke-width:2px
