package com.example.deldrugs.security;

import com.example.deldrugs.dao.UserRepository;
import com.example.deldrugs.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.getByLogin(s);
        if(optionalUser.isPresent()){
            return CustomUserDetails.fromUserEntityToCustomUserDetails(optionalUser.get());
        }else throw new UsernameNotFoundException("User Not Found with username: " + s);
    }
}
