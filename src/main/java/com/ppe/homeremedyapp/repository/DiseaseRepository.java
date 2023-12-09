package com.ppe.homeremedyapp.repository;

import com.ppe.homeremedyapp.models.Disease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Integer> {
    // Additional query methods if needed
    Page<Disease> findByNameContaining(String name, Pageable pageable);
    Optional<Disease> findFirstByUmlsCode(String umlsCode);
}