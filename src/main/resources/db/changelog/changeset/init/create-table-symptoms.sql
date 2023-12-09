CREATE TABLE IF NOT EXISTS Symptoms (
  symptom_id SERIAL PRIMARY KEY,
  umls_code VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  UNIQUE(umls_code)
);