package com.ppe.homeremedyapp.controller;

import com.ppe.homeremedyapp.repository.DiseaseMedicineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ppe.homeremedyapp.models.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/disease-medicines")
public class DiseaseMedicineController {

    @Autowired
    private DiseaseMedicineRepository service;


    @GetMapping
    public List<DiseaseMedicine> getAllDiseaseMedicines() {
        return service.findAll();
    }

    @PostMapping("/disease")
    public List<Medicine> getAllMedicinesByDisease(@RequestBody Disease disease) {
        log.info("" + disease);
        return service.findMedicinesByDisease(disease);
    }

    @PostMapping("/diseases")
    public List<Object[]> getAllMedicinesByDiseases(@RequestBody List<Disease> disease) {
        log.info("" + disease);
        log.info(String.valueOf(service.findMedicinesAndDiseasesCountAndNamesByDiseases(disease)));
        return service.findMedicinesAndDiseasesCountAndNamesByDiseases(disease);
    }


    @PostMapping("/medicine")
    public List<Disease> getAllDiseasesByMedicine(@RequestBody Medicine medicine) {
        log.info("" + medicine);
        return service.findDiseasesByMedicine(medicine);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiseaseMedicine> getDiseaseMedicineById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DiseaseMedicine createDiseaseMedicine(@RequestBody DiseaseMedicine diseaseMedicine) {
        return service.save(diseaseMedicine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiseaseMedicine> updateDiseaseMedicine(@PathVariable Integer id, @RequestBody DiseaseMedicine updatedDiseaseMedicine) {
        return service.findById(id)
                .map(diseaseMedicine -> {
                    updatedDiseaseMedicine.setDiseaseMedicineId(id);
                    return ResponseEntity.ok(service.save(updatedDiseaseMedicine));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiseaseMedicine(@PathVariable Integer id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
