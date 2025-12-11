package com.company.lims.authuserservice.service;

import com.company.lims.authuserservice.dto.request.LoginRequest;
import com.company.lims.authuserservice.dto.response.LoginResponse;
import com.company.lims.authuserservice.security.s_service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService; // UserDetailsService implement edən service
    private final JwtService service;

    public LoginResponse login(LoginRequest request) {
        // 1. Authentication yoxlanışı
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(request.getUserName());

        String token = service.generateToken(userDetails);
        String refreshToken = service.generateRefreshToken(new HashMap<>(), userDetails);


        LoginResponse response = new LoginResponse();

        response.setToken(token);
        response.setRefreshToken(refreshToken);
        return response;
    }
}
