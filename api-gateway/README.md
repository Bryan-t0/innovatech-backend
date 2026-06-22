# API Gateway

Punto unico de entrada para el frontend React de Innovatech Solutions. Expone rutas compatibles para proyectos, recursos, analitica y dashboard integrado.

## Puerto

`8080`

## Endpoints

- `GET /api/dashboard`
- `GET /dashboard`
- `GET/POST/PUT/DELETE /api/projects`
- `GET/POST/PUT/DELETE /projects`
- `GET/POST/PUT/DELETE /api/resources`
- `GET/POST/PUT/DELETE /resources`
- `GET/POST/PUT/DELETE /api/analytics`
- `GET/POST/PUT/DELETE /analytics`

## Funcion

El gateway no usa base de datos. Recibe las solicitudes del frontend, las envia al microservicio correspondiente y construye el dashboard llamando a los tres servicios. Si un microservicio no responde, retorna una lista vacia para esa seccion y mantiene disponible el dashboard.

Las llamadas estan protegidas con Resilience4j. El estado de cada circuito se consulta en `GET /actuator/circuitbreakers`.

## Ejecucion

```bash
mvn spring-boot:run
```

## Pruebas

```bash
mvn verify
```

Reporte: `target/site/jacoco/index.html`. La compilacion falla si la cobertura de lineas baja de 60%.
