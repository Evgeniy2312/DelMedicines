package com.example.deldrugs.dto.telephone;


import com.example.deldrugs.util.ManagerMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelephoneDTO {

    private long id;

    @NotBlank(message = ManagerMessage.BLANK_ERROR)
    @Size(min = 7, max = 12, message = ManagerMessage.NUMBER_USER_ERROR)
    private String number;
}
