INSERT INTO checkout_sessions (id, tenant_id, patient_id, amount, status)
VALUES
  (1, 1, 1, 125.50, 'PAID'),
  (2, 1, 2, 75.00, 'STARTED'),
  (3, 2, 3, 210.25, 'FAILED')
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('checkout_sessions', 'id'), (SELECT COALESCE(MAX(id), 1) FROM checkout_sessions));

