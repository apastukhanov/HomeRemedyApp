package com.ppe.homeremedyapp.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;


@Data
public class DiseasesBySymptomsDto {

    private Disease diseases;

    private Integer countSymptoms;

    private String symptomsNames;
}