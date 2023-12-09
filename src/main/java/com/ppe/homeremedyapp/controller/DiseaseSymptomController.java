package com.ppe.homeremedyapp.controller;


import com.ppe.homeremedyapp.models.Disease;
import com.ppe.homeremedyapp.models.DiseaseSymptom;
import com.ppe.homeremedyapp.models.DiseasesBySymptomsDto;
import com.ppe.homeremedyapp.models.Symptom;
import com.ppe.homeremedyapp.repository.DiseaseSymptomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/disease-symptoms")
public class DiseaseSymptomController {

    @Autowired
    private DiseaseSymptomRepository service;

    @GetMapping
    public List<DiseaseSymptom> getAllDiseaseSymptoms() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiseaseSymptom> getDiseaseSymptomById(@PathVariable Integer id) {
        Optional<DiseaseSymptom> diseaseSymptom = service.findById(id);
        return diseaseSymptom.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/disease")
    public List<Symptom> getAllDiseaseSymptomsByDisease(@RequestBody Disease disease) {
        log.info("" + disease);
        return service.findSymptomsByDisease(disease);
    }


    @PostMapping("/symptoms")
    public List<Object[]> getAllDiseasesBySymptoms(@RequestBody List<Symptom> symptoms) {
        log.info("" + symptoms);
        log.info(String.valueOf(service.findDiseasesAndSymptomCountAndNamesBySymptoms(symptoms)));
        return service.findDiseasesAndSymptomCountAndNamesBySymptoms(symptoms);
    }

    @PostMapping
    public DiseaseSymptom createDiseaseSymptom(@RequestBody DiseaseSymptom diseaseSymptom) {
        return service.save(diseaseSymptom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiseaseSymptom> updateDiseaseSymptom(@PathVariable Integer id, @RequestBody DiseaseSymptom updatedDiseaseSymptom) {
        return service.findById(id)
                .map(diseaseSymptom -> {
                    updatedDiseaseSymptom.setDiseaseSymptomId(id);
                    return ResponseEntity.ok(service.save(updatedDiseaseSymptom));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiseaseSymptom(@PathVariable Integer id) {
        Optional<DiseaseSymptom> diseaseSymptom = service.findById(id);
        if (diseaseSymptom.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

