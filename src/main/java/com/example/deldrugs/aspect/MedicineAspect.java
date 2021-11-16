package com.example.deldrugs.aspect;


import com.example.deldrugs.dto.medicine.*;
import com.example.deldrugs.dto.order.OrderDTO;
import com.example.deldrugs.dto.order.OrderStatusDTO;
import com.example.deldrugs.entity.medicine.Medicine;
import com.example.deldrugs.entity.order.Order;
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
public class MedicineAspect {

    private final Logger logger = LoggerFactory.getLogger(MedicineAspect.class);


    @Pointcut("execution(public * com.example.deldrugs.controller.MedicineController.addMedicine(..)) && args(medicine, ..)")
    public void addMedicine(MedicineForRegDTO medicine) {
    }


    @Pointcut("execution(public * com.example.deldrugs.controller.MedicineController.addMedicines(..)) && args(list, ..)")
    public void addMedicines(List<MedicineForRegDTO> list) {
    }

    @Pointcut("execution(public * com.example.deldrugs.controller.MedicineController.updateMedicine(..)) && args(medicine)")
    public void updateMedicine(MedicineInfoForUpdateDTO medicine) {
    }

    @Pointcut("execution(public * com.example.deldrugs.controller.MedicineController.deleteMedicine(..)) && args(id)")
    public void deleteMedicine(long id) {
    }


    @Pointcut("execution(public * com.example.deldrugs.controller.MedicineController.getById(..)) && args(id)")
    public void getById(long id) {
    }

    @Pointcut("execution(public * com.example.deldrugs.controller.MedicineController.addAmountOfMedicine(..)) && args(medicine)")
    public void addAmountMedicine(MedicineIdAndAmountDTO medicine) {
    }

    @Pointcut("execution(public * com.example.deldrugs.controller.MedicineController.updatePharmacyOfMedicine(..)) && args(id, ..)")
    public void updatePharmacyOfMedicine(long id) {
    }

    @Pointcut("execution(public * com.example.deldrugs.controller.MedicineController.getByPharmacy(..)) && args(id)")
    public void getByPharmacy(long id) {
    }

    @Pointcut("execution(public * com.example.deldrugs.controller.MedicineController.getByCategory(..)) && args(category)")
    public void getByCategory(String category) {
    }


    @Pointcut("execution(public * com.example.deldrugs.controller.MedicineController.getByPricesAndCategory(..)) && args(medicine)")
    public void getByPricesAndCategory(MedicineForGetByPricesDTO medicine) {
    }

    @Pointcut("execution(public * com.example.deldrugs.controller.MedicineController.getByNameUserCategory(..)) && args(medicine)")
    public void getByNameUserCategory(MedicineNameUserTypeDTO medicine) {
    }


    @AfterReturning(pointcut = "addMedicine(medicine)", argNames = "medicine, resp", returning = "resp")
    public void addMed(MedicineForRegDTO medicine, ResponseEntity<Medicine> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Medicine with name {} and category {} was added", medicine.getName(), medicine.getCategory());
        }
    }

    @AfterReturning(pointcut = "addMedicines(list)", argNames = "list, resp", returning = "resp")
    public void addMeds(List<MedicineForRegDTO> list, ResponseEntity<List<Medicine>> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Medicines in size {} were added", list.size());
        }
    }

    @AfterReturning(pointcut = "updateMedicine(medicine)", argNames = "medicine, resp", returning = "resp")
    public void updMed(MedicineInfoForUpdateDTO medicine, ResponseEntity<Medicine> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Medicine with name {} was updated", medicine.getName());
        }
    }

    @AfterReturning(pointcut = "getById(id)", argNames = "id, resp", returning = "resp")
    public void getMedicineById(long id, ResponseEntity<MedicineForResponseDTO> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Medicine with id {} was received", id);
        }
    }

    @AfterReturning(pointcut = "deleteMedicine(id)", argNames = "id, resp", returning = "resp")
    public void delMed(long id, ResponseEntity<Medicine> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Medicine with id {} was deleted", id);
        }
    }

    @AfterReturning(pointcut = "addAmountMedicine(medicine)", argNames = "medicine, resp", returning = "resp")
    public void addAmountOfMed(MedicineIdAndAmountDTO medicine, ResponseEntity<Medicine> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Medicine in the amount of {} was added", medicine.getAmount());
        }
    }


    @AfterReturning(pointcut = "updatePharmacyOfMedicine(id)", argNames = "id, resp", returning = "resp")
    public void updatePharmacyOfMed(long id, ResponseEntity<Medicine> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Medicine with id {} was update owner", id);
        }
    }

    @AfterReturning(pointcut = "getByPharmacy(userId)", argNames = "userId, resp", returning = "resp")
    public void getMedsByPharmacy(long userId, ResponseEntity<List<MedicineForResponseDTO>> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Medicines with id of owner {} were received", userId);
        }
    }

    @AfterReturning(pointcut = "getByCategory(category)", argNames = "category, resp", returning = "resp")
    public void getMedsByCategory(String category, ResponseEntity<List<MedicineForResponseDTO>> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Medicines with category {} were received", category);
        }
    }

    @AfterReturning(pointcut = "getByPricesAndCategory(medicine)", argNames = "medicine, resp", returning = "resp")
    public void getMedsByPricesAndCategory(MedicineForGetByPricesDTO medicine, ResponseEntity<List<MedicineForResponseDTO>> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Medicines with price from {} to {} and category {} were received", medicine.getMin(), medicine.getMax(), medicine.getType());
        }
    }

    @AfterReturning(pointcut = "getByNameUserCategory(medicine)", argNames = "medicine, resp", returning = "resp")
    public void getMedByNameUserAndCategory(MedicineNameUserTypeDTO medicine, ResponseEntity<MedicineForResponseDTO> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("Medicine with name {} nad category {} was received", medicine.getName(), medicine.getType());
        }
    }





}