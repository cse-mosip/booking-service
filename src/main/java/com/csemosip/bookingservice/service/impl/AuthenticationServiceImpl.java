package com.csemosip.bookingservice.service.impl;

import com.csemosip.bookingservice.dto.AuthDTO;
import com.csemosip.bookingservice.dto.AuthenticationResponse;
import com.csemosip.bookingservice.repository.UserRepository;
import com.csemosip.bookingservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse login(AuthDTO authDTO) {
        AuthenticationResponse response = new AuthenticationResponse();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDTO.getUsername(),
                            authDTO.getPassword()
                    )
            );
            var user = userRepository.findByUsername(authDTO.getUsername()).orElseThrow();
            var token = jwtService.generateToken(user);
            response.setSuccessStatus(true);
            response.setToken(token);
        }
        catch (BadCredentialsException exception){
            response.setSuccessStatus(false);
            response.setToken("CANNOT GENERATE");
        }
        catch (AuthenticationException exception){
            response.setSuccessStatus(false);
            response.setToken("CANNOT GENERATE");
        }
        return response;
    }

    @Override
    public AuthenticationResponse logout() {
        return null;
    }
}
