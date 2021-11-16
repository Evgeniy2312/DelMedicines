package com.example.deldrugs.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserForResponseDTO {


    private long id;

    private String name;

    private String login;

    private String role;

    private List<String> number;

    private String city;

    private String street;

    private String home;
}
