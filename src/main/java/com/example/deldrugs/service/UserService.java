package com.example.deldrugs.service;


import com.example.deldrugs.dao.AddressRepository;
import com.example.deldrugs.dao.TelephoneRepository;
import com.example.deldrugs.dao.UserRepository;
import com.example.deldrugs.entity.Address;
import com.example.deldrugs.entity.Telephone;
import com.example.deldrugs.entity.user.Role;
import com.example.deldrugs.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final TelephoneRepository telephoneRepository;
    private final AddressRepository addressRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public boolean addUser(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean delete(long id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getById(id);
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getByLogin(String login) {
        return userRepository.getByLogin(login);
    }

    public User findByLogin(String login){
        return userRepository.findByLogin(login);
    }

    public boolean changePassword(User user, String oldPassword, String newPassword) {
        if ( passwordEncoder.matches(oldPassword, user.getPassword()) && !oldPassword.equals(newPassword)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }


    public boolean addTelephone(User user, Telephone telephone) {
        if (user.getTelephones().contains(telephone)) {
            return false;
        }
        user.getTelephones().add(telephone);
        userRepository.save(user);
        return true;
    }

    public boolean updateTelephone(User user, Telephone telephone) {
        if (isExistTelephoneForUpdate(user.getTelephones(), telephone)) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean deleteTelephone(User user, Telephone telephone) {
        if (user.getTelephones().contains(telephone) && user.getTelephones().size() > 1) {
            user.getTelephones().remove(telephone);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private boolean isExistTelephoneForUpdate(List<Telephone> list, Telephone telephone) {
        Telephone telephone1 = telephoneRepository.getById(telephone.getId());
        if (telephone.getNumber().equals(telephone1.getNumber())) {
            return false;
        } else {
            list.set(list.indexOf(telephone1), telephone);
            return true;
        }
    }

    public boolean updateAddress(User user, Address address) {
        if (user.getAddress().equals(address)) {
            return false;
        }
        user.setAddress(address);
        userRepository.save(user);
        return true;
    }


    public Optional<User> getPharmacyByAddress(Address address) {
        return userRepository.getUserByAddressAndRole(address, Role.ROLE_PHARMACY);
    }

    public List<User>  getPharmaciesByName(String name){
        return userRepository.getUsersByNameAndRole(name, Role.ROLE_PHARMACY);
    }

    public boolean updateName(User user, String newName) {
        if (user.getName().equals(newName)) {
            return false;
        }
        user.setName(newName);
        userRepository.save(user);
        return true;
    }


    public boolean updateLogin(User user, String newLogin) {
        if (user.getLogin().equals(newLogin)) {
            return false;
        }
        user.setName(newLogin);
        userRepository.save(user);
        return true;
    }

    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }


}
