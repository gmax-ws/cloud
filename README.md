# Spring Cloud Netflix microservices PoC

## Spring Boot 2.3.x
## Hoxton.SR12 Spring Cloud dependencies

- Spring Cloud Netflix microservices
  - Zuul - service gateway and router with load balancing.
  - Eureka - Service registry (service discovery)
  - Configuration server, centralized configuration
  - Turbine - aggregates multiple Hystrix Metrics Streams
  - Service BUS
  - Microservices samples
    - Documented with OpenAPI Swagger 3.0
      - /v3/api-docs
      - /v3/api-docs.yaml
      - /swagger-ui.html
  - UI applications examples
- ELK stack (log monitoring)
  - Elasticsearch
  - Logstash
  - Kibana
  - Filebeat
- IDAM server (Keycloak integration)
  - Authentication and authorization

All components are deployed and running inside Docker containers.