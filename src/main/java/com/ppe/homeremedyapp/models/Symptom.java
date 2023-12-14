package com.ppe.homeremedyapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Symptoms")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "symptom_generator")
    @SequenceGenerator(name = "symptom_generator", sequenceName = "symptoms_seq", allocationSize = 1)
    private Integer symptomId;

    @Column(nullable = false, unique = true)
    private String umlsCode;

    @Column(nullable = false)
    private String description;

}
