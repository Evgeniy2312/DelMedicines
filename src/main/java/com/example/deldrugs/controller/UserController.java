package com.example.deldrugs.controller;


import com.example.deldrugs.dto.address.AddressDTO;
import com.example.deldrugs.dto.telephone.TelephoneDTO;
import com.example.deldrugs.dto.user.*;
import com.example.deldrugs.entity.Address;
import com.example.deldrugs.entity.user.User;
import com.example.deldrugs.service.UserService;
import com.example.deldrugs.util.ConverterDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {


    private final UserService userService;



    @PostMapping("/reg")
    public ResponseEntity<UserForRegDTO> registration(@Valid @RequestBody UserForRegDTO userRegDTO) {
        if (userService.addUser(ConverterDTO.getFromReg(userRegDTO))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<UserForResponseDTO> getById(@PathVariable long id) {
        Optional<User> optionalUser = userService.getById(id);
        if (optionalUser.isPresent()) {
            return new ResponseEntity<>(ConverterDTO.getUserForResponse(optionalUser.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getByLogin/{login}")
    public ResponseEntity<UserForResponseDTO> getByLogin(@PathVariable String login) {
        Optional<User> optionalUser = userService.getByLogin(login);
        if (optionalUser.isPresent()) {
            return new ResponseEntity<>(ConverterDTO.getUserForResponse(optionalUser.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<User> changePassword(@Valid @RequestBody UserChangePasswordDTO userChangePasswordDTO, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (userService.changePassword(user, userChangePasswordDTO.getOldPassword(),
                userChangePasswordDTO.getNewPassword()
        )) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/addTelephone")
    public ResponseEntity<User> addTelephone(@Valid @RequestBody TelephoneDTO telephoneDTO, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (userService.addTelephone(user, ConverterDTO.getTelephoneForAdd(telephoneDTO))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/deleteTelephone")
    public ResponseEntity<User> deleteTelephone(@Valid @RequestBody TelephoneDTO telephoneDTO, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (userService.deleteTelephone(user, ConverterDTO.getTelephoneForUpdateAndDelete(telephoneDTO))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/updateTelephone")
    public ResponseEntity<User> updateTelephone(@Valid @RequestBody TelephoneDTO telephoneDTO, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (userService.updateTelephone(user, ConverterDTO.getTelephoneForUpdateAndDelete(telephoneDTO))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/updateAddress")
    public ResponseEntity<User> updateAddress(@Valid @RequestBody AddressDTO addressDTO, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (userService.updateAddress(user, ConverterDTO.getAddressForUpdateOrSearch(addressDTO))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/getPharmacyByAddress")
    public ResponseEntity<UserForResponseDTO> getByAddress(@Valid @RequestBody AddressDTO addressDTO){
        Optional<User> userOptional = userService.getPharmacyByAddress(ConverterDTO.getAddressForUpdateOrSearch(addressDTO));
        if(userOptional.isPresent()){
            User pharmacy = userOptional.get();
            return new ResponseEntity<>(ConverterDTO.getUserForResponse(pharmacy), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/updateLogin")
    public ResponseEntity<User> updateLogin(@Valid @RequestBody UserLoginDTO userLoginDTO, HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        if(userService.updateLogin(user, userLoginDTO.getNewLogin())){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/updateName")
    public ResponseEntity<User> updateName(@Valid @RequestBody UserNameDTO userNameDTO, HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        if(userService.updateName(user, userNameDTO.getNewName())){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getPharmacyByName/{name}")
    public ResponseEntity<List<UserForResponseDTO>> getPharmacies(@PathVariable String name){
        List<User> pharmacies = userService.getPharmaciesByName(name);
        if(pharmacies.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ConverterDTO.getUsersForResponse(pharmacies), HttpStatus.OK);
    }

    @GetMapping("/getAllAddresses")
    public ResponseEntity<List<Address>> getAllAddresses(){
        List<Address> addresses = userService.getAllAddresses();
        if(addresses.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }


}


