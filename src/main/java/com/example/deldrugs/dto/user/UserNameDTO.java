package com.example.deldrugs.dto.user;

import com.example.deldrugs.util.ManagerMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserNameDTO {


    @NotNull(message = ManagerMessage.NULL_ERROR)
    @NotEmpty(message = ManagerMessage.EMPTY_ERROR)
    @NotBlank(message = ManagerMessage.BLANK_ERROR)
    private String newName;
}
