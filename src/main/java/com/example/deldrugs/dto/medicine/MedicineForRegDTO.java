package com.example.deldrugs.dto.medicine;


import com.example.deldrugs.entity.user.User;
import com.example.deldrugs.util.ManagerMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineForRegDTO {


    @NotNull(message = ManagerMessage.NULL_ERROR)
    @NotEmpty(message = ManagerMessage.EMPTY_ERROR)
    @NotBlank(message = ManagerMessage.BLANK_ERROR)
    private String name;

    @NotNull(message = ManagerMessage.NULL_ERROR)
    @NotEmpty(message = ManagerMessage.EMPTY_ERROR)
    @NotBlank(message = ManagerMessage.BLANK_ERROR)
    private String description;

    @Min(value = 1, message = ManagerMessage.AMOUNT_MEDICINE_ERROR_MIN)
    @Max(value = 100, message = ManagerMessage.AMOUNT_MEDICINE_ERROR_MAX )
    private int amount;

    private double price;

    @JsonIgnore
    private User user;

    @NotNull(message = ManagerMessage.NULL_ERROR)
    @NotEmpty(message = ManagerMessage.EMPTY_ERROR)
    @NotBlank(message = ManagerMessage.BLANK_ERROR)
    private String category;
}
