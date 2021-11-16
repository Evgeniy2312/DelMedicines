package com.example.deldrugs.controller;

import com.example.deldrugs.dto.jwtresp.JwtResponseDTO;
import com.example.deldrugs.dto.user.UserAutDTO;
import com.example.deldrugs.entity.user.Role;
import com.example.deldrugs.entity.user.User;
import com.example.deldrugs.security.CustomUserDetails;
import com.example.deldrugs.security.jwt.JwtUtils;
import com.example.deldrugs.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    @PostMapping
    public ResponseEntity<JwtResponseDTO> authorization(@Valid @RequestBody UserAutDTO userAutDTO, HttpSession session) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAutDTO.getUsername(), userAutDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        User user = userService.findByLogin(userAutDTO.getUsername());
        session.setAttribute("user", user);
        String token = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.status(HttpStatus.OK).body(JwtResponseDTO.builder()
                .login(user.getLogin())
                .token(token)
                .roles(roles)
                .build());


    }


}
