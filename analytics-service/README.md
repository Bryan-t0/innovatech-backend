# Analytics Service

Microservicio Spring Boot para la gestion de KPI, metricas de avance y analitica operacional.

## Puerto

`8083`

## Base de datos

`innovatech_analytics_db`

## Patrones usados

- Repository Pattern: `AnalyticsRepository`
- Factory Method: `AnalyticsFactory`
- DTO Pattern
- REST Controller

## Endpoints

- `GET /api/analytics`
- `GET /api/analytics/{id}`
- `POST /api/analytics`
- `PUT /api/analytics/{id}`
- `DELETE /api/analytics/{id}`

## Ejecucion

```bash
mvn spring-boot:run
```
