package com.ppe.homeremedyapp.repository;

import com.ppe.homeremedyapp.models.Medicine;
import com.ppe.homeremedyapp.models.MedicineUse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicineUseRepository extends JpaRepository<MedicineUse, Integer> {

    @Modifying
    @Transactional
    void deleteAllByMedicine(Medicine medicine);

    Optional<MedicineUse> findFirstByMedicine(Medicine medicine);
}
