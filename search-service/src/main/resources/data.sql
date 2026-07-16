INSERT INTO patient_history_index (id, tenant_id, patient_id, summary, occurred_at)
VALUES
  (1, 1, 1, 'Annual cardiology checkup, blood pressure stable', '2026-01-10T09:30:00Z'),
  (2, 1, 1, 'Prescribed follow-up lab tests after cardiology visit', '2026-02-15T11:00:00Z'),
  (3, 1, 2, 'Neurology consultation for recurring headaches', '2026-03-20T13:15:00Z'),
  (4, 2, 3, 'Family medicine visit for seasonal allergy symptoms', '2026-04-05T08:45:00Z'),
  (5, 3, 4, 'Pediatric wellness check with routine vaccination review', '2026-05-12T10:20:00Z')
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('patient_history_index', 'id'), (SELECT COALESCE(MAX(id), 1) FROM patient_history_index));

