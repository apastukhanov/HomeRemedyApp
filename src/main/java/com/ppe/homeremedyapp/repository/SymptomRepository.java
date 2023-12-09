package com.ppe.homeremedyapp.repository;

import com.ppe.homeremedyapp.models.Symptom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Integer> {

    Page<Symptom> findByDescriptionContaining(String description, Pageable pageable);
}