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

## Circuit Breaker Con Resilience4j

El gateway implementa Circuit Breaker real con Spring Cloud Circuit Breaker y Resilience4j. La clase `MicroservicesClient` concentra las llamadas HTTP y usa `@CircuitBreaker` con una instancia independiente para proyectos, recursos y analitica.

Cada circuito evalua las ultimas cinco llamadas. Si al menos el 50 % falla, pasa de `CLOSED` a `OPEN` y responde inmediatamente mediante su fallback. Luego de diez segundos pasa a `HALF_OPEN` y permite dos llamadas de prueba antes de cerrar el circuito o volver a abrirlo.

Los fallbacks de consulta retornan listas vacias para que `/api/dashboard` siga funcionando. Los fallbacks de operaciones CRUD retornan HTTP `503 Service Unavailable`. El estado puede observarse en `/actuator/health`.

La clase separada es necesaria porque las anotaciones de Resilience4j funcionan mediante el proxy AOP de Spring; una llamada interna dentro de la misma clase no seria interceptada.
