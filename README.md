TaskFlow Enterprise

TaskFlow Enterprise is a cloud-native project and task management platform built using a microservices architecture. The platform demonstrates enterprise-grade authentication, authorization, observability, and containerization practices using Spring Boot, PostgreSQL, Flutter, Docker, Ory Kratos, Ory Hydra, and Permify.

The system consists of independent User and Project services secured through OAuth2/OpenID Connect authentication and fine-grained relationship-based access control. The application is fully containerized and designed to be deployed locally using Docker Compose and later scaled using Kubernetes.

Key Features
User registration and authentication using Ory Kratos
OAuth2/OpenID Connect token management using Ory Hydra
Fine-grained authorization using Permify
Project and task management
JWT-based API security
Request logging and auditing using Spring Interceptors
Authentication and security enforcement using Spring Filters
PostgreSQL persistence
Dockerized microservices architecture
Kubernetes-ready deployment structure
Flutter cross-platform frontend
Technology Stack

Backend:

Java 21
Spring Boot 3
Spring Security
PostgreSQL

Frontend:

Flutter

Identity & Access Management:

Ory Kratos
Ory Hydra
Permify

Infrastructure:

Docker
Docker Compose
Kubernetes (planned)
Architecture
Flutter
    │
    ▼
API Gateway
    │
 ┌──┴───────────────┐
 │                  │
 ▼                  ▼

User Service    Project Service
 │                  │
 ▼                  ▼

Postgres       Postgres

Authentication:
Kratos → Hydra → JWT

Authorization:
Permify
