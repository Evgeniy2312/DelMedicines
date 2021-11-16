package com.example.deldrugs.dto.user;


import com.example.deldrugs.util.ManagerMessage;
import com.example.deldrugs.util.Patterns;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordDTO {



    @Pattern(regexp = Patterns.PASSWORD, message = ManagerMessage.PASSWORD_ERROR)
    String oldPassword;


    @Pattern(regexp = Patterns.PASSWORD, message = ManagerMessage.PASSWORD_ERROR)
    String newPassword;


}
