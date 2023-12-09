package com.ppe.homeremedyapp.models;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
public class MedicineExtended {

    private String name;
    private LocalDate expirationDate;
    private String useDescription;
    private List<Disease> diseases;

}
