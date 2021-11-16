package com.example.deldrugs.controller;

import com.example.deldrugs.dto.medicine.*;
import com.example.deldrugs.entity.medicine.CategoryOfMedicine;
import com.example.deldrugs.entity.medicine.Medicine;
import com.example.deldrugs.entity.user.User;
import com.example.deldrugs.service.MedicineService;
import com.example.deldrugs.util.ConverterDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/medicine")
@AllArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;


    @PreAuthorize("hasRole('PHARMACY')")
    @PostMapping("/addMedicine")
    public ResponseEntity<Medicine> addMedicine(@Valid @RequestBody MedicineForRegDTO medicine, HttpSession httpSession) {
        User pharmacy = (User) httpSession.getAttribute("user");
        medicine.setUser(pharmacy);
        if (medicineService.addMedicine(ConverterDTO.getMedicineForReg(medicine))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('PHARMACY')")
    @PostMapping("/addMedicines")
    public ResponseEntity<List<Medicine>> addMedicines(@Valid @RequestBody List<MedicineForRegDTO> list, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (medicineService.addMedicines(ConverterDTO.getMedicinesForReg(list), user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('PHARMACY')")
    @PutMapping("/update")
    public ResponseEntity<Medicine> updateMedicine(@Valid @RequestBody MedicineInfoForUpdateDTO medicineInfoForUpdateDTO) {
        if (medicineService.updateMedicine(ConverterDTO.getMedicineForUpd(medicineInfoForUpdateDTO))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PreAuthorize("hasRole('PHARMACY')")
    @PutMapping("/delete/{id}")
    public ResponseEntity<Medicine> deleteMedicine(@PathVariable long id) {
        if (medicineService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<MedicineForResponseDTO> getById(@PathVariable long id) {
        Optional<Medicine> medicineOptional = medicineService.getById(id);
        if (medicineOptional.isPresent()) {
            return new ResponseEntity<>(ConverterDTO.getMedicineForResponse(medicineOptional.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PreAuthorize("hasRole('PHARMACY')")
    @PutMapping("/addAmountMedicine")
    public ResponseEntity<Medicine> addAmountOfMedicine(@Valid @RequestBody MedicineIdAndAmountDTO amountDTO) {
        if (medicineService.addAmountOfMedicine(amountDTO.getId(), amountDTO.getAmount())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('PHARMACY')")
    @PutMapping("/updatePharmacy/{id}")
    public ResponseEntity<Medicine> updatePharmacyOfMedicine(@PathVariable long id, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (medicineService.updatePharmacy(id, user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getByPharmacy/{userId}")
    public ResponseEntity<List<MedicineForResponseDTO>> getByPharmacy(@PathVariable long userId) {
        List<Medicine> medicines = medicineService.getByUser(userId);
        if (medicines.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ConverterDTO.getMedicinesForResponse(medicines), HttpStatus.OK);
    }


    @GetMapping("/getByCategory/{category}")
    public ResponseEntity<List<MedicineForResponseDTO>> getByCategory(@PathVariable String category) {
        List<Medicine> medicines = medicineService.getByCategory(CategoryOfMedicine.valueOf(category.toUpperCase(Locale.ENGLISH)));
        if (medicines.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ConverterDTO.getMedicinesForResponse(medicines), HttpStatus.OK);
    }


    @GetMapping("/getByPrices")
    public ResponseEntity<List<MedicineForResponseDTO>> getByPricesAndCategory(@Valid @RequestBody MedicineForGetByPricesDTO medicine) {
        List<Medicine> medicines = medicineService.getByPrice(medicine.getMin(), medicine.getMax(),
                CategoryOfMedicine.valueOf(medicine.getType().toUpperCase(Locale.ENGLISH)));
        if (medicines.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ConverterDTO.getMedicinesForResponse(medicines), HttpStatus.OK);
    }

    @GetMapping("/getByNameUserCategory")
    public ResponseEntity<MedicineForResponseDTO> getByNameUserCategory(@Valid @RequestBody MedicineNameUserTypeDTO nameUserTypeDTO) {
        Optional<Medicine> optionalMedicine = medicineService.getByNameAndPharmacyAndCategory(nameUserTypeDTO.getName(),
                nameUserTypeDTO.getUserId(), CategoryOfMedicine.valueOf(nameUserTypeDTO.getType().toUpperCase(Locale.ENGLISH)));
        if (optionalMedicine.isPresent()) {
            return new ResponseEntity<>(ConverterDTO.getMedicineForResponse(optionalMedicine.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
