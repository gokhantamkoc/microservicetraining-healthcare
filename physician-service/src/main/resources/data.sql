INSERT INTO physicians (id, tenant_id, full_name, specialty, active)
VALUES
  (1, 1, 'Dr. Ada Lovelace', 'Cardiology', true),
  (2, 1, 'Dr. Alan Turing', 'Neurology', true),
  (3, 2, 'Dr. Grace Hopper', 'Family Medicine', true),
  (4, 3, 'Dr. Katherine Johnson', 'Pediatrics', true)
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('physicians', 'id'), (SELECT COALESCE(MAX(id), 1) FROM physicians));

