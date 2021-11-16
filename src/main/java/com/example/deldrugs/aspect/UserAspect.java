package com.example.deldrugs.aspect;

import com.example.deldrugs.dto.address.AddressDTO;
import com.example.deldrugs.dto.jwtresp.JwtResponseDTO;
import com.example.deldrugs.dto.telephone.TelephoneDTO;
import com.example.deldrugs.dto.user.*;
import com.example.deldrugs.entity.Address;
import com.example.deldrugs.entity.user.User;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class UserAspect {

    private final Logger logger = LoggerFactory.getLogger(UserAspect.class);

    @Pointcut("execution(public * com.example.deldrugs.controller.UserController.registration(..)) && args(user)")
    public void reg(UserForRegDTO user) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.UserController.getById(..)) && args(id)")
    public void getById(long id) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.UserController.getByLogin(..)) && args(login)")
    public void getByLogin(String login) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.UserController.changePassword(..)) && args(userChangePasswordDTO, ..)")
    public void changePassword(UserChangePasswordDTO userChangePasswordDTO) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.UserController.addTelephone(..)) && args(telephoneDTO, ..)")
    public void addTelephone(TelephoneDTO telephoneDTO) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.UserController.deleteTelephone(..)) && args(telephoneDTO, ..)")
    public void deleteTelephone(TelephoneDTO telephoneDTO) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.UserController.updateAddress(..)) && args(addressDTO, ..)")
    public void updateAddress(AddressDTO addressDTO) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.UserController.updateTelephone(..)) && args(telephoneDTO, ..)")
    public void updateTelephone(TelephoneDTO telephoneDTO) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.UserController.getByAddress(..)) && args(addressDTO)")
    public void getPharmacyByAddress(AddressDTO addressDTO) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.UserController.updateLogin(..)) && args(userLoginDTO, ..)")
    public void updateLogin(UserLoginDTO userLoginDTO) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.UserController.updateName(..)) && args(userNameDTO, ..)")
    public void updateName(UserNameDTO userNameDTO) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.UserController.getPharmacies(..)) && args(name)")
    public void getPharmaciesByName(String name) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.UserController.getAllAddresses(..))")
    public void getAllAddresses() {}


    @AfterReturning(pointcut = "reg(user)", argNames = "user, resp", returning = "resp")
    public void registration(UserForRegDTO user, ResponseEntity<UserForRegDTO> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("User with name {} registered", user.getName());
        }
    }

    @AfterReturning(pointcut = "getById(id)", argNames = "id, resp", returning = "resp")
    public void userById(long id, ResponseEntity<UserForResponseDTO> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("User with id - {} was received", id);
        }
    }

    @AfterReturning(pointcut = "getByLogin(login)" , argNames = "login, resp", returning = "resp")
    public void userByLog(String login, ResponseEntity<UserForResponseDTO> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("User with login - {} was received", login);
        }
    }

    @AfterReturning(pointcut = "changePassword(userChangePasswordDTO)" , argNames = "userChangePasswordDTO, resp", returning = "resp")
    public void changePass(UserChangePasswordDTO userChangePasswordDTO, ResponseEntity<User> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("User changed password");
        }
    }

    @AfterReturning(pointcut = "addTelephone(telephoneDTO)" , argNames = "telephoneDTO, resp", returning = "resp")
    public void addTel(TelephoneDTO telephoneDTO, ResponseEntity<User> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Telephone was added by user");
        }
    }


    @AfterReturning(pointcut = "deleteTelephone(telephoneDTO)" , argNames = "telephoneDTO, resp", returning = "resp")
    public void deleteTel(TelephoneDTO telephoneDTO, ResponseEntity<User> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Telephone {} was deleted by user", telephoneDTO.getNumber());
        }
    }

    @AfterReturning(pointcut = "updateTelephone(telephoneDTO)" , argNames = "telephoneDTO, resp", returning = "resp")
    public void updateTel(TelephoneDTO telephoneDTO, ResponseEntity<User> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Telephone {} was updated by user", telephoneDTO.getNumber());
        }
    }


    @AfterReturning(pointcut = "getPharmacyByAddress(addressDTO)" , argNames = "addressDTO, resp", returning = "resp")
    public void getByAddress(AddressDTO addressDTO,  ResponseEntity<UserForResponseDTO> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Pharmacies to the certainly address were received");
        }
    }


    @AfterReturning(pointcut = "updateAddress(addressDTO)" , argNames = "addressDTO, resp", returning = "resp")
    public void updateAddr(AddressDTO addressDTO,  ResponseEntity<User> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Address was updated by user");
        }
    }

    @AfterReturning(pointcut = "updateLogin(userLoginDTO)" , argNames = "userLoginDTO, resp", returning = "resp")
    public void updateLog(UserLoginDTO userLoginDTO,  ResponseEntity<User> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("User updated login");
        }
    }

    @AfterReturning(pointcut = "updateName(userNameDTO)" , argNames = "userNameDTO, resp", returning = "resp")
    public void updName(UserNameDTO userNameDTO,  ResponseEntity<User> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("User updated name to {}", userNameDTO.getNewName());
        }
    }

    @AfterReturning(pointcut = "getPharmaciesByName(name)" , argNames = "name, resp", returning = "resp")
    public void getPharByName(String name,  ResponseEntity<List<UserForResponseDTO>> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("All pharmacies with name {} were received", name);
        }
    }

    @AfterReturning(pointcut = "getAllAddresses()" , argNames = "resp", returning = "resp")
    public void getAllAddr(ResponseEntity<List<Address>> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("All addresses were received");
        }
    }





}
