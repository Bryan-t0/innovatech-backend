# Projects Service

Microservicio Spring Boot para la gestion de proyectos de Innovatech.

## Puerto

`8081`

## Base de datos

`innovatech_projects_db`

## Patrones usados

- Repository Pattern: `ProjectRepository`
- Factory Method: `ProjectFactory`
- DTO Pattern
- REST Controller

## Endpoints

- `GET /api/projects`
- `GET /api/projects/{id}`
- `POST /api/projects`
- `PUT /api/projects/{id}`
- `DELETE /api/projects/{id}`

## Ejecucion

```bash
mvn spring-boot:run
```
