INSERT INTO analytics_metrics(metric_name, metric_type, metric_value, description, generated_date)
SELECT 'Avance promedio de proyectos', 'PROJECT_PROGRESS', 48.3, 'Promedio general de avance de proyectos activos', '2026-05-14'
WHERE NOT EXISTS (SELECT 1 FROM analytics_metrics WHERE metric_name = 'Avance promedio de proyectos');

INSERT INTO analytics_metrics(metric_name, metric_type, metric_value, description, generated_date)
SELECT 'Utilizacion de recursos', 'RESOURCE_USAGE', 66.6, 'Porcentaje de recursos actualmente asignados', '2026-05-14'
WHERE NOT EXISTS (SELECT 1 FROM analytics_metrics WHERE metric_name = 'Utilizacion de recursos');

INSERT INTO analytics_metrics(metric_name, metric_type, metric_value, description, generated_date)
SELECT 'Proyectos completados', 'COMPLETED_PROJECTS', 1.0, 'Cantidad de proyectos finalizados', '2026-05-14'
WHERE NOT EXISTS (SELECT 1 FROM analytics_metrics WHERE metric_name = 'Proyectos completados');