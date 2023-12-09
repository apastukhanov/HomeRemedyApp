package com.ppe.homeremedyapp.controller;

import com.ppe.homeremedyapp.models.*;
import com.ppe.homeremedyapp.repository.DiseaseMedicineRepository;
import com.ppe.homeremedyapp.repository.MedicineRepository;
import com.ppe.homeremedyapp.repository.MedicineUseRepository;
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
@RequestMapping("/api/medicines")
public class MedicineController {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private DiseaseMedicineRepository diseaseMedicineRepository;

    @Autowired
    private MedicineUseRepository medicineUseRepository;

    // POST: Create a new medicine
    @PostMapping
    public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) {
        Medicine savedMedicine = medicineRepository.save(medicine);
        return new ResponseEntity<>(savedMedicine, HttpStatus.CREATED);
    }

    // PUT: Update an existing medicine
    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Integer id, @RequestBody Medicine medicineDetails) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id " + id));
        medicine.setName(medicineDetails.getName());
        medicine.setExpirationDate(medicineDetails.getExpirationDate());
        final Medicine updatedMedicine = medicineRepository.save(medicine);
        return ResponseEntity.ok(updatedMedicine);
    }

    // DELETE: Delete a medicine
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMedicine(@PathVariable Integer id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id " + id));
        diseaseMedicineRepository.deleteAllByMedicine(medicine);
        medicineUseRepository.deleteAllByMedicine(medicine);
        medicineRepository.delete(medicine);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // GET: Retrieve a medicine based on its id
    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable Integer id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id " + id));
        return ResponseEntity.ok(medicine);
    }

    // GET: Retrieve all medicines with pagination
//    @GetMapping
//    public ResponseEntity<Page<Medicine>> getAllMedicines(Pageable pageable) {
//        return new ResponseEntity<>(medicineRepository.findAll(pageable), HttpStatus.OK);
//    }

    @PostMapping("/extended")
    public ResponseEntity<Medicine> createDiseaseExt(@RequestBody MedicineExtended medExt) {
        log.info("diseaseExt is " + medExt);

        Optional<Medicine> optionalMedicine  = medicineRepository.findFirstByName(medExt.getName());

        Medicine medicine;

        if (!optionalMedicine.isPresent()) {
            Integer lastIndex = 9999;
            medicine = new Medicine();
            medicine.setMedicineId(lastIndex);
        } else {
            medicine = optionalMedicine.get();
        }

        medicine.setName(medExt.getName());
        medicine.setExpirationDate(medExt.getExpirationDate());

        Medicine savedMed = medicineRepository.save(medicine);

        diseaseMedicineRepository.deleteAllByMedicine(savedMed);


        if (medExt.getDiseases() != null){
            medExt.getDiseases().forEach(d -> {
                DiseaseMedicine ds =  new DiseaseMedicine();
                ds.setMedicine(savedMed);
                ds.setDisease(d);
                diseaseMedicineRepository.save(ds);
            });
        }

        medicineUseRepository.deleteAllByMedicine(savedMed);

        MedicineUse mu =  new MedicineUse();
        mu.setMedicine(savedMed);
        mu.setUseDescription(medExt.getUseDescription());
        medicineUseRepository.save(mu);

        return new ResponseEntity<>(savedMed, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Medicine>> getAllMedicinesByPage(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                                    Sort.by(Sort.Direction.ASC,"medicineId"));
        Page<Medicine> sortedDiseases = medicineRepository.findAll(pageable);
        log.info("The first entry:" + sortedDiseases.stream().findFirst());
        return new ResponseEntity<>(sortedDiseases, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Medicine>> getMedicinesByDescription(
            @RequestParam(value = "name") String name,
            Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                                        Sort.by(Sort.Direction.ASC,"medicineId"));
        log.info("Get mapping pageable:" + name);
        return ResponseEntity.ok(medicineRepository.findByNameContaining(name, pageable));
    }
}
