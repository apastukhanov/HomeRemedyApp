package com.ppe.homeremedyapp.repository;

import com.ppe.homeremedyapp.models.Disease;
import com.ppe.homeremedyapp.models.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

    Page<Medicine> findByNameContaining(String name, Pageable pageable);

    Optional<Medicine> findFirstByName(String name);
}