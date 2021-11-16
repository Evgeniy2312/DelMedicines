package com.example.deldrugs.dto.user;


import com.example.deldrugs.util.ManagerMessage;
import com.example.deldrugs.util.Patterns;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {


    @NotBlank(message = ManagerMessage.BLANK_ERROR)
    @Pattern(regexp = Patterns.EMAIL, message = ManagerMessage.EMAIL_ERROR)
    private String newLogin;

}
