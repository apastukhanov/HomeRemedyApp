package com.ppe.homeremedyapp.controller;

import com.ppe.homeremedyapp.models.Disease;
import com.ppe.homeremedyapp.models.Medicine;
import com.ppe.homeremedyapp.models.MedicineUse;
import com.ppe.homeremedyapp.repository.MedicineUseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/medicineuses")
public class MedicineUseController {

    private final MedicineUseRepository medicineUseRepository;

    @Autowired
    public MedicineUseController(MedicineUseRepository medicineUseRepository) {
        this.medicineUseRepository = medicineUseRepository;
    }

    @GetMapping
    public ResponseEntity<List<MedicineUse>> getAllMedicineUses() {
        List<MedicineUse> medicineUses = medicineUseRepository.findAll();
        return new ResponseEntity<>(medicineUses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineUse> getMedicineUseById(@PathVariable Integer id) {
        MedicineUse medicineUse = medicineUseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine Use not found with id " + id));
        return new ResponseEntity<>(medicineUse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MedicineUse> createMedicineUse(@RequestBody MedicineUse medicineUse) {
        MedicineUse createdMedicineUse = medicineUseRepository.save(medicineUse);
        return new ResponseEntity<>(createdMedicineUse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicineUse> updateMedicineUse(@PathVariable Integer id, @RequestBody MedicineUse updatedMedicineUse) {
        MedicineUse medicineUse = medicineUseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine Use not found with id " + id));


        MedicineUse updatedMedicine = medicineUseRepository.save(medicineUse);
        return new ResponseEntity<>(updatedMedicine, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicineUse(@PathVariable Integer id) {
        MedicineUse medicineUse = medicineUseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine Use not found with id " + id));

        medicineUseRepository.delete(medicineUse);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/medicine")
    public MedicineUse getAllDiseasesByMedicine(@RequestBody Medicine medicine) {
        log.info("" + medicine);
        log.info("" + new MedicineUse());
        return medicineUseRepository.findFirstByMedicine(medicine).orElse(new MedicineUse());
    }

}

