package com.ppe.homeremedyapp.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "diseasemedicine")
public class DiseaseMedicine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diseasemedicine_generator")
    @SequenceGenerator(name = "diseasemedicine_generator", sequenceName = "diseasemedicine_seq", allocationSize = 1)
    private Integer diseaseMedicineId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "diseaseId", referencedColumnName = "diseaseId", nullable = false)
    private Disease disease;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medicineId", referencedColumnName = "medicineId",  nullable = false)
    private Medicine medicine;

}

