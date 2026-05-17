# Analisis De Patrones

Este documento complementa el informe de Innovatech Solutions y resume los patrones aplicados en la Evaluacion Parcial 2.

## Microservicios

La solucion se separa en tres dominios independientes:

- `projects-service`: gestion de proyectos.
- `resources-service`: gestion de recursos humanos.
- `analytics-service`: metricas e indicadores.

Cada microservicio tiene su propio proyecto Maven, aplicacion Spring Boot, controlador REST, servicio, repositorio JPA y base de datos MySQL.

## API Gateway

`api-gateway` actua como punto unico de entrada para el frontend. Expone `/api/projects`, `/api/resources`, `/api/analytics` y `/api/dashboard`, ademas de rutas equivalentes sin `/api` para compatibilidad con React.

## Base De Datos Por Servicio

Se usan tres bases de datos:

- `innovatech_projects_db`
- `innovatech_resources_db`
- `innovatech_analytics_db`

Esto mantiene los datos desacoplados por dominio.

## Repository Pattern

Implementado con Spring Data JPA:

- `ProjectRepository`
- `ResourceRepository`
- `AnalyticsRepository`

## Factory Method

Implementado en:

- `ProjectFactory`
- `ResourceFactory`
- `AnalyticsFactory`

Cada factory transforma DTOs de entrada en entidades antes de persistirlas.

## Circuit Breaker Basico

El gateway usa manejo de excepciones con `try/catch` para evitar que una falla en un microservicio deje sin respuesta al dashboard. Si un servicio falla, el gateway devuelve una lista vacia para esa seccion y agrega un mensaje controlado.
