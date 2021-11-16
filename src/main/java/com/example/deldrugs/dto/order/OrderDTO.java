package com.example.deldrugs.dto.order;


import com.example.deldrugs.dto.medicine.MedicineIdAndAmountDTO;
import com.example.deldrugs.entity.Address;
import com.example.deldrugs.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private List<MedicineIdAndAmountDTO> medicines = new ArrayList<>();
    @JsonIgnore
    private User user;
    private Address address;
    private LocalDateTime localDateTime;
}
