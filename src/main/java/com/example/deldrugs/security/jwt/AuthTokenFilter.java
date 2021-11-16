package com.example.deldrugs.security.jwt;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter{

    private static final String AUTHORIZATION = "Authorization";

    private final JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    private static final List<String> AUTH_WHITELIST = Arrays.asList(
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/",
            "/v3/api-docs/",
            "/swagger-ui/",
            "/h2-console",
            "/auth",
            "/user/reg"
    );


    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean flag = false;
        try {
            String jwt = parseJwt(request);
            if (checkWhiteList(request.getRequestURI())) {
                flag = true;
            } else {
                if (jwt != null) {
                    flag = jwtUtils.validateJwtToken(jwt);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }
        if(!flag){
            response.sendError(404);
        }else filterChain.doFilter(request, response);
    }

    private boolean checkWhiteList(String pathURI){
        for (String s : AUTH_WHITELIST) {
            if (pathURI.startsWith(s)) {
                return true;
            }
        }
        return false;
    }
}
