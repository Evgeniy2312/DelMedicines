package com.example.deldrugs.aspect;


import com.example.deldrugs.dto.medicine.MedicineForResponseDTO;
import com.example.deldrugs.dto.order.OrderDTO;
import com.example.deldrugs.dto.user.UserForRegDTO;
import com.example.deldrugs.dto.user.UserForResponseDTO;
import com.example.deldrugs.entity.order.Order;
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
public class AdminAspect {

    private final Logger logger = LoggerFactory.getLogger(AdminAspect.class);

    @Pointcut("execution(public * com.example.deldrugs.controller.AdminController.regPharmacy(..)) && args(user)")
    public void regPharmacy(UserForRegDTO user) {}


    @Pointcut("execution(public * com.example.deldrugs.controller.AdminController.deleteUser(..)) && args(id)")
    public void deleteUser(long id) {}

    @Pointcut("execution(public * com.example.deldrugs.controller.AdminController.getAllUsers(..))")
    public void getAllUsers() {}

    @Pointcut("execution(public * com.example.deldrugs.controller.AdminController.getAllMedicines(..))")
    public void getAllMedicines() {}

    @Pointcut("execution(public * com.example.deldrugs.controller.AdminController.getAllOrders(..))")
    public void getAllOrders() {}


    @Pointcut("execution(public * com.example.deldrugs.controller.AdminController.deleteOrder(..)) && args(orderId)")
    public void deleteOrder(long orderId) {}


    @AfterReturning(pointcut = "regPharmacy(user)", argNames = "user, resp", returning = "resp")
    public void addPharmacy(UserForRegDTO user, ResponseEntity<User> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Pharmacy with name {} was registered", user.getName());
        }
    }

    @AfterReturning(pointcut = "deleteUser(userId)", argNames = "userId, resp", returning = "resp")
    public void delUser(long userId, ResponseEntity<User> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("User with id - {} was deleted", userId);
        }
    }

    @AfterReturning(pointcut = "deleteOrder(orderId)", argNames = "orderId, resp", returning = "resp")
    public void delOrder(long orderId, ResponseEntity<Order> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Order with id - {} was deleted", orderId);
        }
    }

    @AfterReturning(pointcut = "getAllOrders()", returning = "resp")
    public void getOrders(ResponseEntity<List<OrderDTO>> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("All orders are received");
        }
    }

    @AfterReturning(pointcut = "getAllMedicines()", returning = "resp")
    public void getMedicines(ResponseEntity<List<MedicineForResponseDTO>> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("All medicines are received");
        }
    }

    @AfterReturning(pointcut = "getAllUsers()", returning = "resp")
    public void getUsers(ResponseEntity<List<UserForResponseDTO>> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("All users are received");
        }
    }




}
