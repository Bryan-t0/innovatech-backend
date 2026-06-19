# Innovatech Solutions - Backend Microservicios

Backend para la Evaluacion Parcial 2 de Desarrollo Fullstack III. Implementa un API Gateway y tres microservicios con Spring Boot, Maven, JPA, MySQL y comunicacion REST.

## Arquitectura

Frontend React -> API Gateway -> Projects Service / Resources Service / Analytics Service -> MySQL independiente por servicio.

## Componentes

- `api-gateway`: punto unico de entrada para el frontend.
- `projects-service`: gestiona proyectos, estados y avance.
- `resources-service`: gestiona recursos humanos y asignaciones.
- `analytics-service`: gestiona KPIs y metricas.

## Puertos

- `api-gateway`: 8080
- `projects-service`: 8081
- `resources-service`: 8082
- `analytics-service`: 8083
- Frontend React: 5173

## Bases de datos

```sql
CREATE DATABASE innovatech_projects_db;
CREATE DATABASE innovatech_resources_db;
CREATE DATABASE innovatech_analytics_db;
```

## Como ejecutar

1. Crear las bases de datos en MySQL.
2. Configurar usuario y password en `application.properties` de cada microservicio.
3. Ejecutar `projects-service`.
4. Ejecutar `resources-service`.
5. Ejecutar `analytics-service`.
6. Ejecutar `api-gateway`.
7. Probar `http://localhost:8080/api/dashboard`.

Comandos:

```bash
cd projects-service
mvn spring-boot:run

cd ../resources-service
mvn spring-boot:run

cd ../analytics-service
mvn spring-boot:run

cd ../api-gateway
mvn spring-boot:run
```

## Endpoints

Projects:

- `GET /api/projects`
- `GET /api/projects/{id}`
- `POST /api/projects`
- `PUT /api/projects/{id}`
- `DELETE /api/projects/{id}`

Resources:

- `GET /api/resources`
- `GET /api/resources/{id}`
- `POST /api/resources`
- `PUT /api/resources/{id}`
- `DELETE /api/resources/{id}`

Analytics:

- `GET /api/analytics`
- `GET /api/analytics/{id}`
- `POST /api/analytics`
- `PUT /api/analytics/{id}`
- `DELETE /api/analytics/{id}`

Dashboard:

- `GET /api/dashboard`

Tambien se mantienen rutas compatibles sin `/api`: `/projects`, `/resources`, `/analytics` y `/dashboard`.

## Patrones aplicados

- Microservicios: separacion por dominios de negocio.
- API Gateway: punto unico de entrada para el frontend.
- Repository Pattern: separacion del acceso a datos con `JpaRepository`.
- Factory Method: creacion de entidades desde DTOs.
- Circuit Breaker: Resilience4j con estados CLOSED, OPEN y HALF_OPEN, fallbacks y monitoreo Actuator.
- Base de datos por servicio: cada microservicio administra su propia base MySQL.
- DTO Pattern: separa contratos REST de entidades JPA.

## Documentacion

- `docs/ANALISIS_PATRONES_ARQUETIPOS.md`
- `docs/PLAN_BRANCHING.md`
- `repositorios.txt`
