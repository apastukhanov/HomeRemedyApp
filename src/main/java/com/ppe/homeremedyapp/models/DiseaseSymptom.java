package com.ppe.homeremedyapp.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "diseasesymptoms")
public class DiseaseSymptom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diseasesymptom_generator")
    @SequenceGenerator(name = "diseasesymptom_generator", sequenceName = "diseasesymptom_seq", allocationSize = 1)
    private Integer diseaseSymptomId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "diseaseId", referencedColumnName = "diseaseId")
    private Disease disease;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "symptomId", referencedColumnName = "symptomId")
    private Symptom symptom;

    @Column
    private Integer diseaseOccurrence = 1;
}
