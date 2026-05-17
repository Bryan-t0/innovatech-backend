# Resources Service

Microservicio Spring Boot para la gestion de recursos humanos, disponibilidad y asignacion a proyectos.

## Puerto

`8082`

## Base de datos

`innovatech_resources_db`

## Patrones usados

- Repository Pattern: `ResourceRepository`
- Factory Method: `ResourceFactory`
- DTO Pattern
- REST Controller

## Endpoints

- `GET /api/resources`
- `GET /api/resources/{id}`
- `POST /api/resources`
- `PUT /api/resources/{id}`
- `DELETE /api/resources/{id}`

## Ejecucion

```bash
mvn spring-boot:run
```
