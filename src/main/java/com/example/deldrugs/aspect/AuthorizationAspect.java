package com.example.deldrugs.aspect;

import com.example.deldrugs.dto.jwtresp.JwtResponseDTO;
import com.example.deldrugs.dto.user.UserAutDTO;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorizationAspect {

    private final Logger logger = LoggerFactory.getLogger(AuthorizationAspect.class);


    @Pointcut("execution(public * com.example.deldrugs.controller.AuthController.authorization(..)) && args(user, ..)")
    public void auth(UserAutDTO user) {}

    @AfterReturning(pointcut = "auth(user)", argNames = "user, resp", returning = "resp")
    public void auth(UserAutDTO user, ResponseEntity<JwtResponseDTO> resp){
        if(resp.getStatusCode().equals(HttpStatus.OK)){
            logger.info("User with login {} authorized", user.getUsername());
        }
    }

}
