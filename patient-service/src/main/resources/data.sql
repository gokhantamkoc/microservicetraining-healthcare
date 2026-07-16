INSERT INTO patients (id, tenant_id, full_name, medical_record_number, phone_number)
VALUES
  (1, 1, 'Grace Hopper', 'MRN-A-1001', '+905551112233'),
  (2, 1, 'Margaret Hamilton', 'MRN-A-1002', '+905551112244'),
  (3, 2, 'Donald Knuth', 'MRN-B-2001', '+905551112255'),
  (4, 3, 'Barbara Liskov', 'MRN-C-3001', '+905551112266')
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('patients', 'id'), (SELECT COALESCE(MAX(id), 1) FROM patients));

INSERT INTO medical_history_entries (id, patient_id, summary, occurred_at)
VALUES
  (1, 1, 'Annual cardiology checkup, blood pressure stable', '2026-01-10T09:30:00Z'),
  (2, 1, 'Follow-up lab tests requested after cardiology visit', '2026-02-15T11:00:00Z'),
  (3, 2, 'Neurology consultation for recurring headaches', '2026-03-20T13:15:00Z'),
  (4, 3, 'Family medicine visit for seasonal allergy symptoms', '2026-04-05T08:45:00Z')
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('medical_history_entries', 'id'), (SELECT COALESCE(MAX(id), 1) FROM medical_history_entries));
