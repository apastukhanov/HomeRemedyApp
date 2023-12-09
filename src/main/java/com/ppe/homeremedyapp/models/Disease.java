package com.ppe.homeremedyapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Diseases")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "disease_generator")
    @SequenceGenerator(name = "disease_generator", sequenceName = "diseases_seq", allocationSize = 1)
    private Integer diseaseId;

    @Column(nullable = false, unique = true)
    private String umlsCode;

    @Column(nullable = false)
    private String name;

}
