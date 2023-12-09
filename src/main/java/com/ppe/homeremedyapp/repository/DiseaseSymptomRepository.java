package com.ppe.homeremedyapp.repository;

import com.ppe.homeremedyapp.models.Disease;
import com.ppe.homeremedyapp.models.DiseaseSymptom;
import com.ppe.homeremedyapp.models.DiseasesBySymptomsDto;
import com.ppe.homeremedyapp.models.Symptom;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseSymptomRepository extends JpaRepository<DiseaseSymptom, Integer> {
    // Additional query methods if needed

    @Query("SELECT ds.symptom FROM DiseaseSymptom ds WHERE ds.disease = :disease")
    List<Symptom> findSymptomsByDisease(@Param("disease") Disease disease);
//    List<Symptom> findSymptomsByDisease(Disease disease);

    @Query("SELECT ds.disease FROM DiseaseSymptom ds WHERE ds.symptom = :symptom")
    List<Disease> findDiseasesBySymptom(@Param("symptom") Symptom symptom);

    @Query("SELECT DISTINCT ds.disease FROM DiseaseSymptom ds WHERE ds.symptom IN :symptoms")
    List<Disease> findDiseasesBySymptoms(@Param("symptoms") List<Symptom> symptoms);

    @Query("SELECT DISTINCT d, COUNT(s), STRING_AGG(s.description, ', ') " +
            "FROM DiseaseSymptom ds " +
            "JOIN ds.disease d " +
            "JOIN ds.symptom s " +
            "WHERE s IN :symptoms " +
            "GROUP BY d " +
            "ORDER BY COUNT(s) DESC")
    List<Object[]> findDiseasesAndSymptomCountAndNamesBySymptoms(@Param("symptoms") List<Symptom> symptoms);


    @Modifying
    @Transactional
    void deleteAllByDisease(Disease disease);

    void deleteAllBySymptom(Symptom symptom);
}
