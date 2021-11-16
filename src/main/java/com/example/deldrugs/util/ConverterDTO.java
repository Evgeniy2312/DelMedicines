package com.example.deldrugs.util;

import com.example.deldrugs.dto.address.AddressDTO;
import com.example.deldrugs.dto.medicine.MedicineForRegDTO;
import com.example.deldrugs.dto.medicine.MedicineForResponseDTO;
import com.example.deldrugs.dto.medicine.MedicineIdAndAmountDTO;
import com.example.deldrugs.dto.medicine.MedicineInfoForUpdateDTO;
import com.example.deldrugs.dto.order.OrderDTO;
import com.example.deldrugs.dto.order.OrderStatusDTO;
import com.example.deldrugs.dto.telephone.TelephoneDTO;
import com.example.deldrugs.dto.user.UserForRegDTO;
import com.example.deldrugs.dto.user.UserForResponseDTO;
import com.example.deldrugs.entity.*;
import com.example.deldrugs.entity.medicine.CategoryOfMedicine;
import com.example.deldrugs.entity.medicine.Medicine;
import com.example.deldrugs.entity.order.Order;
import com.example.deldrugs.entity.order.OrderStatus;
import com.example.deldrugs.entity.user.Role;
import com.example.deldrugs.entity.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class ConverterDTO {

    ////////////////////////USER////////////////////////////////////

    public static User getFromReg(UserForRegDTO userRegDTO){
        List<Telephone> telephones = new ArrayList<>();
        userRegDTO.getTelephones().forEach(tel -> telephones.add(Telephone.builder()
                .number(tel.getNumber())
                .build()));
        return User.builder()
                .login(userRegDTO.getLogin())
                .password(userRegDTO.getPassword())
                .name(userRegDTO.getName())
                .role(Role.ROLE_USER)
                .address(Address.builder()
                        .city(userRegDTO.getAddress().getCity())
                        .home(userRegDTO.getAddress().getHome())
                        .street(userRegDTO.getAddress().getStreet())
                        .build())
                .telephones(telephones)
                .build();
    }


    public static User getPharmacyFromReg(UserForRegDTO userRegDTO){
        List<Telephone> telephones = new ArrayList<>();
        userRegDTO.getTelephones().forEach(tel -> telephones.add(Telephone.builder()
                .number(tel.getNumber())
                .build()));
        return User.builder()
                .login(userRegDTO.getLogin())
                .password(userRegDTO.getPassword())
                .name(userRegDTO.getName())
                .role(Role.ROLE_PHARMACY)
                .address(Address.builder()
                        .city(userRegDTO.getAddress().getCity())
                        .home(userRegDTO.getAddress().getHome())
                        .street(userRegDTO.getAddress().getStreet())
                        .build())
                .telephones(telephones)
                .build();
    }

    public static UserForResponseDTO getUserForResponse(User user){
        return UserForResponseDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .role(user.getRole().name())
                .name(user.getName())
                .number(user.getTelephones().stream().map(Telephone::getNumber).collect(Collectors.toList()))
                .city(user.getAddress().getCity())
                .home(user.getAddress().getHome())
                .street(user.getAddress().getStreet())
                .build();
    }

    public static List<UserForResponseDTO> getUsersForResponse(List<User> users){
     List<UserForResponseDTO> pharmacies = new ArrayList<>();
     users.forEach(pharmacy -> pharmacies.add(UserForResponseDTO.builder()
             .id(pharmacy.getId())
             .login(pharmacy.getLogin())
             .name(pharmacy.getName())
             .role(pharmacy.getRole().name())
             .number(pharmacy.getTelephones().stream().map(Telephone::getNumber).collect(Collectors.toList()))
             .city(pharmacy.getAddress().getCity())
             .home(pharmacy.getAddress().getHome())
             .street(pharmacy.getAddress().getStreet())
             .build()));
     return pharmacies;
    }

    ////////////////////////ORDER////////////////////////////////////

    public static Order getOrder(OrderDTO orderDTO){
        List<Medicine> list = new ArrayList<>();
        orderDTO.getMedicines()
                .forEach(med -> list.add(Medicine.builder()
                        .id(med.getId())
                        .amount(med.getAmount())
                .build()));
        return Order.builder()
                .medicine(list)
                .orderStatus(OrderStatus.PLACED)
                .localDateTime(LocalDateTime.now())
                .address(orderDTO.getAddress())
                .user(orderDTO.getUser())
                .build();
    }

    public static Order getOrderForUpd(OrderStatusDTO orderStatusDTO){
        return Order.builder()
                .id(orderStatusDTO.getId())
                .orderStatus(OrderStatus.valueOf(orderStatusDTO.getStatus().toUpperCase(Locale.ENGLISH)))
                .build();
    }

    public static List<OrderDTO> getOrdersForResp(List<Order> orders){
        List<OrderDTO> orderList = new ArrayList<>();
        orders.forEach(order -> orderList.add(OrderDTO.builder()
                .id(order.getId())
                .address(order.getAddress())
                .localDateTime(order.getLocalDateTime())
                .medicines(getMedicines(order.getMedicine()))
                .build()));
        return orderList;
    }

    private static List<MedicineIdAndAmountDTO> getMedicines(List<Medicine> medicines){
        List<MedicineIdAndAmountDTO> medicineIdAndAmountDTOS = new ArrayList<>();
        medicines.forEach(medicine -> medicineIdAndAmountDTOS.add(MedicineIdAndAmountDTO.builder()
                .id(medicine.getId())
                .amount(medicine.getAmount())
                .build()));
        return medicineIdAndAmountDTOS;
    }


    ////////////////////////TELEPHONE////////////////////////////////////
    public static Telephone getTelephoneForAdd(TelephoneDTO telephoneDTO){
        return Telephone.builder()
                .number(telephoneDTO.getNumber())
                .build();
    }

    public static Telephone getTelephoneForUpdateAndDelete(TelephoneDTO telephoneDTO){
        return Telephone.builder()
                .id(telephoneDTO.getId())
                .number(telephoneDTO.getNumber())
                .build();
    }

    public static List<TelephoneDTO> getTelephoneDTOForResponse(List<Telephone> telephones){
        List<TelephoneDTO> telephoneDTOS = new ArrayList<>();
        telephones.forEach(tel -> telephoneDTOS.add(
                TelephoneDTO.builder()
                        .id(tel.getId())
                        .number(tel.getNumber())
                        .build()

        ));
        return telephoneDTOS;
    }///// для admin


    ////////////////////////ADDRESS////////////////////////////////////


    public static Address getAddressForUpdateOrSearch(AddressDTO addressDTO){
        return Address.builder()
                .id(addressDTO.getId())
                .street(addressDTO.getStreet())
                .city(addressDTO.getCity())
                .home(addressDTO.getHome())
                .build();
    }

    public static AddressDTO getAddressDTOForResponse(Address address){
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .home(address.getHome())
                .build();
    }

    /////////////////////////MEDICINE//////////////////////////////

    public static Medicine getMedicineForReg(MedicineForRegDTO medicine){
        return Medicine.builder()
                .name(medicine.getName())
                .description(medicine.getDescription())
                .category(CategoryOfMedicine.valueOf(medicine.getCategory().toUpperCase(Locale.ENGLISH)))
                .price(medicine.getPrice())
                .amount(medicine.getAmount())
                .user(medicine.getUser())
                .build();
    }

    public static List<Medicine> getMedicinesForReg(List<MedicineForRegDTO> medicines){
        List<Medicine> medicineList = new ArrayList<>();
        medicines.forEach(medicine -> medicineList.add(Medicine.builder()
                .name(medicine.getName())
                .description(medicine.getDescription())
                .category(CategoryOfMedicine.valueOf(medicine.getCategory().toUpperCase(Locale.ENGLISH)))
                .price(medicine.getPrice())
                .amount(medicine.getAmount())
                .build()
        ));
        return medicineList;
    }

    public static Medicine getMedicineForUpd(MedicineInfoForUpdateDTO medicine){
        return Medicine.builder()
                .id(medicine.getId())
                .name(medicine.getName())
                .description(medicine.getDescription())
                .category(CategoryOfMedicine.valueOf(medicine.getCategory().toUpperCase(Locale.ENGLISH)))
                .price(medicine.getPrice())
                .build();
    }

    public static MedicineForResponseDTO getMedicineForResponse(Medicine medicine){
        return MedicineForResponseDTO.builder()
                .id(medicine.getId())
                .name(medicine.getName())
                .description(medicine.getDescription())
                .type(medicine.getCategory().name())
                .nameOfPharmacy(medicine.getUser().getName())
                .amount(medicine.getAmount())
                .price(medicine.getPrice())
                .build();
    }

    public static List<MedicineForResponseDTO> getMedicinesForResponse(List<Medicine> medicines){
        List<MedicineForResponseDTO> medicineList = new ArrayList<>();
        medicines.forEach(medicine -> medicineList.add(MedicineForResponseDTO.builder()
                .id(medicine.getId())
                .name(medicine.getName())
                .description(medicine.getDescription())
                .type(medicine.getCategory().name())
                .nameOfPharmacy(medicine.getUser().getName())
                .price(medicine.getPrice())
                .amount(medicine.getAmount())
                .build()));
        return medicineList;
    }



}
