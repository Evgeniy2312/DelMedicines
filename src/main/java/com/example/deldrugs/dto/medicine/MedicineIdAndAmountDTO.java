package com.example.deldrugs.dto.medicine;


import com.example.deldrugs.util.ManagerMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineIdAndAmountDTO {


    private long id;

    @Min(value = 1, message = ManagerMessage.AMOUNT_MEDICINE_ERROR_MIN)
    @Max(value = 99, message = ManagerMessage.AMOUNT_MEDICINE_ERROR_MAX)
    private int amount;
}
