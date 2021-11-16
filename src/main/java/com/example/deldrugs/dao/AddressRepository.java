package com.example.deldrugs.dao;

import com.example.deldrugs.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    boolean existsByCityAndStreetAndHome(String city, String street, String home);
}
