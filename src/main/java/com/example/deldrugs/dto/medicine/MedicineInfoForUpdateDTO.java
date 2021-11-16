package com.example.deldrugs.dto.medicine;


import com.example.deldrugs.entity.user.User;
import com.example.deldrugs.util.ManagerMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineInfoForUpdateDTO {


    private long id;

    @NotNull(message = ManagerMessage.NULL_ERROR)
    @NotEmpty(message = ManagerMessage.EMPTY_ERROR)
    @NotBlank(message = ManagerMessage.BLANK_ERROR)
    private String name;

    @NotNull(message = ManagerMessage.NULL_ERROR)
    @NotEmpty(message = ManagerMessage.EMPTY_ERROR)
    @NotBlank(message = ManagerMessage.BLANK_ERROR)
    private String description;

    @NotNull(message = ManagerMessage.NULL_ERROR)
    @NotEmpty(message = ManagerMessage.EMPTY_ERROR)
    @NotBlank(message = ManagerMessage.BLANK_ERROR)
    private String category;

    @JsonIgnore
    private User user;

    private double price;

}
