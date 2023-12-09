package com.ppe.homeremedyapp.controller;

import com.ppe.homeremedyapp.models.Disease;
import com.ppe.homeremedyapp.models.DiseaseExtended;
import com.ppe.homeremedyapp.models.DiseaseSymptom;
import com.ppe.homeremedyapp.repository.DiseaseMedicineRepository;
import com.ppe.homeremedyapp.repository.DiseaseRepository;
import com.ppe.homeremedyapp.repository.DiseaseSymptomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/diseases")
public class DiseaseController {

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private DiseaseSymptomRepository diseaseSymptomRepository;

    @Autowired
    private DiseaseMedicineRepository diseaseMedicineRepository;

    @PostMapping
    public ResponseEntity<Disease> createDisease(@RequestBody Disease disease) {
        Disease savedDisease = diseaseRepository.save(disease);
        return new ResponseEntity<>(savedDisease, HttpStatus.CREATED);
    }

    @PostMapping("/extended")
    public ResponseEntity<Disease> createDiseaseExt(@RequestBody DiseaseExtended diseaseExt) {
        Optional<Disease> optionalDisease = diseaseRepository.findFirstByUmlsCode(
                                                                diseaseExt.getUmlsCode());
        Disease disease;

        if (!optionalDisease.isPresent()) {
            Integer lastIndex = 9999;
            disease = new Disease();
            disease.setDiseaseId(lastIndex + 1);
        } else {
            disease = optionalDisease.get();
        }

        disease.setUmlsCode(diseaseExt.getUmlsCode());
        disease.setName(diseaseExt.getName());

        Disease savedDisease = diseaseRepository.save(disease);

        diseaseSymptomRepository.deleteAllByDisease(savedDisease);
        if (diseaseExt.getSymptoms() != null){
            diseaseExt.getSymptoms().forEach(symptom -> {
                DiseaseSymptom ds =  new DiseaseSymptom();
                ds.setDisease(savedDisease);
                ds.setSymptom(symptom);
                diseaseSymptomRepository.save(ds);
            });
        }
        return new ResponseEntity<>(savedDisease, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disease> updateDisease(@PathVariable Integer id,
                                                 @RequestBody Disease diseaseDetails) {
        Disease disease = diseaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disease not found with id " + id));
        disease.setName(diseaseDetails.getName());
        disease.setUmlsCode(diseaseDetails.getUmlsCode());
        final Disease updatedDisease = diseaseRepository.save(disease);
        return ResponseEntity.ok(updatedDisease);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDisease(@PathVariable Integer id) {
        Disease disease = diseaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disease not found with id " + id));
        diseaseSymptomRepository.deleteAllByDisease(disease);
        diseaseMedicineRepository.deleteAllByDisease(disease);
        diseaseRepository.delete(disease);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disease> getDiseaseById(@PathVariable Integer id) {
        Disease disease = diseaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disease not found with id " + id));
        return ResponseEntity.ok(disease);
    }

    @GetMapping
    public ResponseEntity<Page<Disease>> getAllDisease(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                                        Sort.by(Sort.Direction.ASC,"diseaseId"));
        Page<Disease> sortedDiseases = diseaseRepository.findAll(pageable);
        return new ResponseEntity<>(sortedDiseases, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Disease>> getDiseasesByDescription(
            @RequestParam(value = "name") String name,
            Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                                    Sort.by(Sort.Direction.ASC,"diseaseId"));
        return ResponseEntity.ok(diseaseRepository.findByNameContaining(name, pageable));
    }
}

