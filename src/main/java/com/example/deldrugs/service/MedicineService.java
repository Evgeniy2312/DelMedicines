package com.example.deldrugs.service;

import com.example.deldrugs.dao.MedicineRepository;
import com.example.deldrugs.entity.medicine.CategoryOfMedicine;
import com.example.deldrugs.entity.medicine.Medicine;
import com.example.deldrugs.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class MedicineService {

    public final static int RESTRICT = 100;
    private final MedicineRepository medicineRepository;



    public boolean addMedicine(Medicine medicine) {
        if (medicineRepository.existsByNameAndUserAndCategory(medicine.getName(), medicine.getUser(),
                medicine.getCategory()) || medicine.getAmount() > 100) {
            return false;
        }
        medicineRepository.save(medicine);
        return true;
    }

    public boolean addMedicines(List<Medicine> medicines, User user){
        if(testAddMedicine(medicines)) {
            for (Medicine medicine : medicines) {
                medicine.setUser(user);
                medicineRepository.save(medicine);
            }
            return true;
        }
        return false;
    }

    private  boolean testAddMedicine(List<Medicine> medicines){
        return medicines.stream().noneMatch(medicine -> medicineRepository.existsByNameAndUserAndCategory(medicine.getName(), medicine.getUser(),
                medicine.getCategory()) && medicine.getAmount() <= 100);
    }

    public boolean updateMedicine(Medicine medicine){
        Optional<Medicine> medicineOptional = medicineRepository.findById(medicine.getId());
        if(medicineOptional.isPresent()){
            medicine.setAmount(medicineOptional.get().getAmount());
            medicine.setUser(medicineOptional.get().getUser());
            medicineRepository.save(medicine);
            return true;
        }
        return false;
    }


    public boolean delete(long id) {
        if (medicineRepository.existsById(id)) {
            medicineRepository.delete(medicineRepository.getById(id));
            return true;
        }
        return false;
    }


    public Optional<Medicine> getById(long id) {
        return medicineRepository.findById(id);
    }

    public List<Medicine> getAll() {
        return medicineRepository.findAll();
    }

    public boolean addAmountOfMedicine(long id, int amount) {
        Optional<Medicine> optionalMedicine = medicineRepository.findById(id);
        if (optionalMedicine.isPresent()) {
            Medicine medicine = optionalMedicine.get();
            if ((medicine.getAmount() + amount) <= RESTRICT) {
                medicine.setAmount(medicine.getAmount() + amount);
                medicineRepository.save(medicine);
                return true;
            }
        }
        return false;
    }

    public boolean updatePharmacy(long id, User user) {
        Optional<Medicine> medicine = medicineRepository.findById(id);
        if (medicine.isPresent() && !medicine.get().getUser().equals(user)) {
            medicine.get().setUser(user);
            medicineRepository.save(medicine.get());
            return true;
        }
        return false;
    }

    public List<Medicine> getByUser(long userId) {
        return medicineRepository.getByUserId(userId);
    }

    public List<Medicine> getByCategory(CategoryOfMedicine category) {
        return medicineRepository.getByCategory(category);
    }

    public Optional<Medicine> getByNameAndPharmacyAndCategory(String name, long userId, CategoryOfMedicine category) {
        return medicineRepository.getByNameAndUserIdAndCategory(name, userId, category);
    }

    public List<Medicine> getByPrice(double min, double max, CategoryOfMedicine category) {
        return medicineRepository.getByPriceBetweenAndCategory(min, max, category);
    }



}
