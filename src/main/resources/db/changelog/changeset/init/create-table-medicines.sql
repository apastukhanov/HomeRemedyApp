CREATE TABLE IF NOT EXISTS Medicines (
    medicine_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    expiration_date DATE NOT NULL,
    UNIQUE(name)
);