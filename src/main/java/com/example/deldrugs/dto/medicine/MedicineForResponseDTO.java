package com.example.deldrugs.dto.medicine;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineForResponseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;


    private String name;


    private String description;

    private String type;

    private double price;

    private String nameOfPharmacy;

    private int amount;
}
