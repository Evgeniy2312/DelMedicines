package com.example.deldrugs.controller;


import com.example.deldrugs.dto.medicine.MedicineForResponseDTO;
import com.example.deldrugs.dto.order.OrderDTO;
import com.example.deldrugs.dto.user.UserForRegDTO;
import com.example.deldrugs.dto.user.UserForResponseDTO;
import com.example.deldrugs.entity.order.Order;
import com.example.deldrugs.entity.user.User;
import com.example.deldrugs.service.MedicineService;
import com.example.deldrugs.service.OrderService;
import com.example.deldrugs.service.UserService;
import com.example.deldrugs.util.ConverterDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;
    private final MedicineService medicineService;
    private final OrderService orderService;



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/regPharmacy")
    public ResponseEntity<User> regPharmacy(@Valid @RequestBody UserForRegDTO user){
        if(userService.addUser(ConverterDTO.getPharmacyFromReg(user))){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/deleteUser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        if (userService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserForResponseDTO>> getAllUsers() {
        if (userService.getAll().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ConverterDTO.getUsersForResponse(userService.getAll()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllMedicines")
    public ResponseEntity<List<MedicineForResponseDTO>> getAllMedicines(){
        if(medicineService.getAll().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ConverterDTO.getMedicinesForResponse(medicineService.getAll()), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        List<Order> orders = orderService.getAll();
        if(orders.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ConverterDTO.getOrdersForResp(orders), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/deleteOrder/{orderId}")
    public ResponseEntity<Order> deleteOrder(@PathVariable long orderId){
        if(orderService.deleteOrder(orderId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
