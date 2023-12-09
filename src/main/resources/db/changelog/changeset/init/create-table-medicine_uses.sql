CREATE TABLE IF NOT EXISTS MedicineUses (
    use_id SERIAL PRIMARY KEY,
    medicine_id INT NOT NULL,
    use_description VARCHAR(255) NOT NULL,
    FOREIGN KEY (medicine_id) REFERENCES Medicines(medicine_id)
);