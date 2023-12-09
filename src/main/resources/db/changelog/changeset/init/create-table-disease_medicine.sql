CREATE TABLE IF NOT EXISTS DiseaseMedicine (
    disease_medicine_id SERIAL PRIMARY KEY,
    disease_id INT NOT NULL,
    medicine_id INT NOT NULL,
    FOREIGN KEY (disease_id) REFERENCES Diseases(disease_id),
    FOREIGN KEY (medicine_id) REFERENCES Medicines(medicine_id)
);