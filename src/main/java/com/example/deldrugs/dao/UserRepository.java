package com.example.deldrugs.dao;

import com.example.deldrugs.entity.Address;
import com.example.deldrugs.entity.user.Role;
import com.example.deldrugs.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;



public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLogin(String login);
    Optional<User> getByLogin(String login);
    User findByLogin(String login);
    List<User> getUsersByNameAndRole(String name, Role role);
    Optional<User> getUserByAddressAndRole(Address address, Role role);
}
