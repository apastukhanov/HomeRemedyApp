CREATE TABLE IF NOT EXISTS Diseases (
      disease_id SERIAL PRIMARY KEY,
      umls_code VARCHAR(255) NOT NULL,
      name VARCHAR(255) NOT NULL,
      UNIQUE(umls_code)
);