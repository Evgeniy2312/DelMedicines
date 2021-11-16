package com.example.deldrugs.dao;

import com.example.deldrugs.entity.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelephoneRepository extends JpaRepository<Telephone, Long> {
    boolean existsByNumber(String number);
}
