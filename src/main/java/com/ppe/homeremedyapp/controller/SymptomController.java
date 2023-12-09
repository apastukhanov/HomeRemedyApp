package com.ppe.homeremedyapp.controller;

import com.ppe.homeremedyapp.models.Symptom;
import com.ppe.homeremedyapp.repository.DiseaseSymptomRepository;
import com.ppe.homeremedyapp.repository.SymptomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/symptoms")
//@CrossOrigin(origins = "http://localhost:4200/")
public class SymptomController {

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private DiseaseSymptomRepository diseaseSymptomRepository;

    // POST: Create a new symptom
    @PostMapping
    public ResponseEntity<Symptom> createSymptom(@RequestBody Symptom symptom) {
        log.info("" +symptom);
        Symptom savedSymptom = symptomRepository.save(symptom);
        return new ResponseEntity<>(savedSymptom, HttpStatus.CREATED);
    }

    // PUT: Update an existing symptom
    @PutMapping("/{id}")
    public ResponseEntity<Symptom> updateSymptom(@PathVariable Integer id, @RequestBody Symptom symptomDetails) {
        Symptom symptom = symptomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Symptom not found with id " + id));
        symptom.setDescription(symptomDetails.getDescription());
        symptom.setUmlsCode(symptomDetails.getUmlsCode());
        final Symptom updatedSymptom = symptomRepository.save(symptom);
        return ResponseEntity.ok(updatedSymptom);
    }

    // DELETE: Delete a symptom
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSymptom(@PathVariable Integer id) {
        Symptom symptom = symptomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Symptom not found with id " + id));
        diseaseSymptomRepository.deleteAllBySymptom(symptom);
//        diseaseSymptomRepository.deleteAllBySymptomId(symptom.getSymptomId());
        symptomRepository.delete(symptom);
        log.info("Deleted: " + symptom);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // GET: Retrieve a symptom based on its id
    @GetMapping("/{id}")
    public ResponseEntity<Symptom> getSymptomById(@PathVariable Integer id) {
        Symptom symptom = symptomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Symptom not found with id " + id));
        return ResponseEntity.ok(symptom);
    }


    @GetMapping
    public ResponseEntity<Page<Symptom>> getAllSymptoms(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC,"symptomId"));
        Page<Symptom> sortedSymptoms = symptomRepository.findAll(pageable);
        log.info("The first entry:" + sortedSymptoms.stream().findFirst());
        return new ResponseEntity<>(sortedSymptoms, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Symptom>> getSymptomsByDescription(
            @RequestParam(value = "name") String name,
            Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC,"symptomId"));
        log.info("Get mapping pageable:" + name);
        return ResponseEntity.ok(symptomRepository.findByDescriptionContaining(name, pageable));
    }
}

