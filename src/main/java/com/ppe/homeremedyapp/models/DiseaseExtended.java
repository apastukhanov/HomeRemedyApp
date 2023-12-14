package com.ppe.homeremedyapp.models;


import lombok.Data;

import java.util.List;

@Data
public class DiseaseExtended {
    private Integer diseaseId;
    private String umlsCode;
    private String name;
    List<Symptom> symptoms;

}
