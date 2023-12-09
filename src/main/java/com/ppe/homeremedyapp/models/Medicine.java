package com.ppe.homeremedyapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "Medicines")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicine_generator")
    @SequenceGenerator(name = "medicine_generator", sequenceName = "medicine_seq", allocationSize = 1)
    private Integer medicineId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDate expirationDate;
}

