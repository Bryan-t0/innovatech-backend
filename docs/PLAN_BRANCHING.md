# Plan De Branching

## Estrategia

Se propone una estrategia Git Flow simplificada para ordenar el trabajo del equipo.

## Ramas

- `main`: version estable y entregable.
- `develop`: integracion de cambios aprobados.
- `feature/api-gateway`: gateway, CORS, proxy y dashboard integrado.
- `feature/projects-service`: microservicio de proyectos.
- `feature/resources-service`: microservicio de recursos.
- `feature/analytics-service`: microservicio de analitica.
- `feature/frontend-integration`: ajustes de consumo desde React.
- `feature/tests`: pruebas unitarias basicas.
- `feature/documentation`: README y documentacion de entrega.

## Flujo De Trabajo

1. Crear una rama `feature/*` desde `develop`.
2. Implementar cambios pequenos y verificables.
3. Ejecutar build y pruebas locales.
4. Integrar los cambios en `develop` mediante merge o Pull Request.
5. Integrar `develop` en `main` cuando todo este probado.

## Aplicacion En El Proyecto

Cada componente evoluciona en una rama separada para reducir conflictos y dejar clara la responsabilidad de cada integrante.
