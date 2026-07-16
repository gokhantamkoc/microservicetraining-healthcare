# Microservice Patterns Used

- Saga: Saga pattern solves transactional operation on microservice level, when a rollback is needed saga pattern helps create compensating operations.
- API Gateway: API gateway pattern gives clients one entry point instead of calling every microservice directly.
- Database per Service: Database per service pattern gives each microservice its own database so services do not share tables.
- Service Discovery: Service discovery pattern lets microservices find each other by service name instead of hardcoded host and port.
- Event-Driven Messaging: Event-driven messaging pattern lets microservices communicate by publishing and consuming messages asynchronously.
- Circuit Breaker: Circuit breaker pattern stops repeated calls to a failing service so the failure does not spread across the system.
- gRPC Communication: gRPC communication is used for fast internal service-to-service calls with shared contracts.
- REST and GraphQL APIs: REST and GraphQL APIs are used for external clients to call the system.
- Hexagonal Architecture: Hexagonal architecture separates domain code from infrastructure code such as database, REST, GraphQL, messaging, and gRPC.
