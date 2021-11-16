package com.example.deldrugs.dao;

import com.example.deldrugs.entity.medicine.CategoryOfMedicine;
import com.example.deldrugs.entity.medicine.Medicine;
import com.example.deldrugs.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    boolean existsByNameAndUserAndCategory(String name, User user, CategoryOfMedicine category);
    List<Medicine> getByUserId(long userId);
    List<Medicine> getByCategory(CategoryOfMedicine category);
    List<Medicine> getByPriceBetweenAndCategory(double price1, double price2, CategoryOfMedicine category);
    Optional<Medicine> getByNameAndUserIdAndCategory(String name, long userID, CategoryOfMedicine category);
}
