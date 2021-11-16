package com.example.deldrugs.dto.user;

import com.example.deldrugs.dto.address.AddressDTO;
import com.example.deldrugs.dto.telephone.TelephoneDTO;
import com.example.deldrugs.util.ManagerMessage;
import com.example.deldrugs.util.Patterns;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserForRegDTO {

    @NotNull(message = ManagerMessage.NULL_ERROR)
    @NotEmpty(message = ManagerMessage.EMPTY_ERROR)
    @NotBlank(message = ManagerMessage.BLANK_ERROR)
    private String name;

    @NotBlank(message = ManagerMessage.BLANK_ERROR)
    @Pattern(regexp = Patterns.EMAIL, message = ManagerMessage.EMAIL_ERROR)
    private String login;


    @Pattern(regexp = Patterns.PASSWORD)
    private String password;
    private List<TelephoneDTO> telephones;
    private AddressDTO address;
}
