INSERT INTO resources(full_name, role, availability, email, assigned_project_id)
SELECT 'Bryan Torres', 'Backend Developer', 'ASSIGNED', 'bryan@innovatech.cl', 1
WHERE NOT EXISTS (SELECT 1 FROM resources WHERE email = 'bryan@innovatech.cl');

INSERT INTO resources(full_name, role, availability, email, assigned_project_id)
SELECT 'Jorge Fuentes', 'Frontend Developer', 'AVAILABLE', 'jorge@innovatech.cl', NULL
WHERE NOT EXISTS (SELECT 1 FROM resources WHERE email = 'jorge@innovatech.cl');

INSERT INTO resources(full_name, role, availability, email, assigned_project_id)
SELECT 'Angel Estay', 'QA Tester', 'ASSIGNED', 'angel@innovatech.cl', 2
WHERE NOT EXISTS (SELECT 1 FROM resources WHERE email = 'angel@innovatech.cl');