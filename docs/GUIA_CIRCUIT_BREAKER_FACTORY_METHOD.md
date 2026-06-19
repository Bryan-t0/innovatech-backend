# Guia: Circuit Breaker Y Factory Method

## Circuit Breaker

La implementacion sigue el estilo del ejemplo del profesor: dependencia Resilience4j, anotacion `@CircuitBreaker`, un nombre que coincide con `application.properties` y un metodo fallback compatible.

Archivos principales:

- `api-gateway/pom.xml`: agrega Actuator, AOP y Resilience4j.
- `api-gateway/.../service/MicroservicesClient.java`: llamadas remotas, anotaciones y fallbacks.
- `api-gateway/.../service/ServiceCallResult.java`: informa al dashboard si se uso un fallback.
- `api-gateway/.../service/DashboardService.java`: compone la respuesta sin capturar excepciones manualmente.
- `api-gateway/src/main/resources/application.properties`: configura los tres circuitos.

Flujo:

1. El controlador llama a `DashboardService`.
2. `DashboardService` llama al bean `MicroservicesClient`.
3. El proxy AOP de Spring intercepta `@CircuitBreaker`.
4. Si el microservicio responde, Resilience4j registra un exito.
5. Si falla, Resilience4j registra la falla y ejecuta el fallback.
6. Al superar el umbral, el circuito queda `OPEN` y evita nuevas llamadas durante diez segundos.

## Factory Method

Los Factory Method ya estan implementados en los tres servicios:

- `ProjectFactory.fromRequest`
- `ResourceFactory.fromRequest`
- `AnalyticsFactory.fromRequest`

Cada metodo recibe un DTO de entrada y crea la entidad de dominio. Los servicios usan estas fabricas antes de guardar con el repositorio. Esto mantiene la construccion de entidades fuera del controlador y del acceso a datos.

## Prueba Manual

1. Levantar los tres microservicios y el gateway.
2. Consultar `GET http://localhost:8080/api/dashboard`.
3. Detener, por ejemplo, `projects-service`.
4. Repetir cinco veces la consulta al dashboard.
5. Verificar que el dashboard responde y muestra el mensaje de fallback.
6. Consultar `GET http://localhost:8080/actuator/health` y verificar `projectsService` en estado `OPEN`.
7. Esperar diez segundos, volver a levantar el servicio y hacer dos consultas; el circuito debe pasar por `HALF_OPEN` y regresar a `CLOSED`.

Una operacion CRUD dirigida al servicio detenido responde con HTTP 503 y un mensaje controlado.
