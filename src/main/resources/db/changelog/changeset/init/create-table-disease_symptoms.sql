CREATE TABLE IF NOT EXISTS DiseaseSymptoms (
     disease_symptom_id SERIAL PRIMARY KEY,
     disease_id INT NOT NULL,
     symptom_id INT NOT NULL,
     disease_occurrence INT,
     FOREIGN KEY (disease_id) REFERENCES Diseases(disease_id),
     FOREIGN KEY (symptom_id) REFERENCES Symptoms(symptom_id)
);