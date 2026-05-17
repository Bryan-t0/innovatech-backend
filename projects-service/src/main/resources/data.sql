INSERT INTO projects(name, description, status, start_date, end_date, progress)
SELECT 'Plataforma Gestion Interna', 'Sistema para centralizar la gestion de proyectos internos', 'IN_PROGRESS', '2026-05-01', '2026-07-30', 45
WHERE NOT EXISTS (SELECT 1 FROM projects WHERE name = 'Plataforma Gestion Interna');

INSERT INTO projects(name, description, status, start_date, end_date, progress)
SELECT 'Migracion Cloud', 'Migracion de servicios internos hacia infraestructura cloud', 'PLANNED', '2026-06-01', '2026-09-15', 0
WHERE NOT EXISTS (SELECT 1 FROM projects WHERE name = 'Migracion Cloud');

INSERT INTO projects(name, description, status, start_date, end_date, progress)
SELECT 'Dashboard KPI', 'Panel para monitorear indicadores clave de desempeno', 'COMPLETED', '2026-03-01', '2026-04-30', 100
WHERE NOT EXISTS (SELECT 1 FROM projects WHERE name = 'Dashboard KPI');