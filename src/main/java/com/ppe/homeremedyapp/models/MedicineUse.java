package com.ppe.homeremedyapp.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "medicineuses")
public class MedicineUse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicineuses_generator")
    @SequenceGenerator(name = "medicineuses_generator", sequenceName = "medicineuses_seq", allocationSize = 1)
    private Integer useId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medicineId", referencedColumnName = "medicineId",  nullable = false)
    private Medicine medicine;

    @Column(nullable = false)
    private String useDescription = "";

}

