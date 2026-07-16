# Healthcare Microservices Interview Project Plan

## Summary
Create a new Maven parent project at `microservicetraining-healthcare`, leaving `healthcare-platform-core` untouched. The project is a runnable Java 25 / Spring Boot / Spring Cloud training system for interview preparation.

Modules:
- `discovery-server`
- `api-gateway`
- `grpc-contracts`
- `tenant-service`
- `physician-service`
- `patient-service`
- `search-service`
- `checkout-service`
- `notification-service`

Business services use hexagonal architecture:
`domain`, `application`, `adapter/in/*`, and `adapter/out/*`.

## Key Changes
- Parent Maven project:
  - `groupId`: `com.gokhantamkoc.microservicetraining.healthcare`
  - Java `25`
  - Spring Boot `4.1.0`
  - Spring Cloud `2025.1.2`
- Infrastructure:
  - Eureka service discovery.
  - Spring Cloud Gateway as external entrypoint.
  - RabbitMQ for domain events and notification provider routing.
  - PostgreSQL database per service.
  - Docker Compose for the complete local system.
- Domain model:
  - `tenant-service`: tenant/clinic, appointment, announcement, notification preference.
  - `physician-service`: physician and specialty.
  - `patient-service`: patient and medical history.
  - `search-service`: patient history search index.
  - `checkout-service`: bill checkout and mock payment saga.
  - `notification-service`: notification delivery and provider routing.
- API and communication examples:
  - REST controllers for synchronous outside calls.
  - Asynchronous REST examples returning `202 Accepted`.
  - GraphQL query/mutation examples.
  - Shared gRPC `.proto` contracts for synchronous and asynchronous internal calls.
  - RabbitMQ routing keys for tenant-selected notification providers.
- N+1 avoidance:
  - GraphQL resolvers are designed around batch lookup methods.
  - Repositories expose `findByIdIn`-style methods where parent-child loading is needed.

## Test Plan
- Run `mvn clean verify` from the parent.
- Verify service bootstrapping for all modules.
- Smoke test through the gateway:
  - create tenant
  - create physician
  - create patient
  - search patient history
  - start checkout
  - verify notification delivery row
- Validate RabbitMQ notification routes:
  - `notification.aws-sns`
  - `notification.twilio`
  - `notification.fcm`

## Assumptions
- The implementation is a runnable skeleton for interview prep, not a production-grade platform.
- Existing `healthcare-platform-core` is left untouched.
- Tenant domains are designed minimally because no tenant/appointment/announcement source files were present in the repository.
- Payment gateway integration is mocked.
- Notification providers are mocked by database writes.

