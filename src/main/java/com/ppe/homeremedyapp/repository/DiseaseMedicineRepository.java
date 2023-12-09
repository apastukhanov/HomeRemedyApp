package com.ppe.homeremedyapp.repository;

import com.ppe.homeremedyapp.models.Disease;
import com.ppe.homeremedyapp.models.DiseaseMedicine;
import com.ppe.homeremedyapp.models.Medicine;
import com.ppe.homeremedyapp.models.Symptom;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseMedicineRepository extends JpaRepository<DiseaseMedicine, Integer> {
    // Additional query methods if needed


    @Query("SELECT dm.medicine FROM DiseaseMedicine dm WHERE dm.disease = :disease")
    List<Medicine> findMedicinesByDisease(@Param("disease") Disease disease);

    @Query("SELECT dm.disease FROM DiseaseMedicine dm WHERE dm.medicine = :medicine")
    List<Disease> findDiseasesByMedicine(@Param("medicine") Medicine medicine);

    @Query("SELECT DISTINCT dm.medicine FROM DiseaseMedicine dm WHERE dm.disease IN :disease")
    List<Medicine> findMedicinesByDiseases(@Param("disease") List<Disease> disease);

    @Query("SELECT DISTINCT m, COUNT(d), STRING_AGG(d.name, ', ') " +
            "FROM DiseaseMedicine dm " +
            "JOIN dm.medicine m " +
            "JOIN dm.disease d " +
            "WHERE d IN :diseases " +
            "GROUP BY m " +
            "ORDER BY COUNT(d) DESC")
    List<Object[]> findMedicinesAndDiseasesCountAndNamesByDiseases(@Param("diseases") List<Disease> diseases);


    @Modifying
    @Transactional
    void deleteAllByMedicine(Medicine medicine);

    @Modifying
    @Transactional
    void deleteAllByDisease(Disease disease);
}
