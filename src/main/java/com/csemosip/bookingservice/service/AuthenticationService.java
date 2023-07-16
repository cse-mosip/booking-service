package com.csemosip.bookingservice.service;

import com.csemosip.bookingservice.dto.AuthDTO;
import com.csemosip.bookingservice.model.AuthenticationResponse;
import com.csemosip.bookingservice.repository.UserRepository;
import com.csemosip.bookingservice.service.Impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServiceImpl {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse login(AuthDTO authDTO) {
        AuthenticationResponse response = new AuthenticationResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDTO.getUsername(),
                        authDTO.getPassword()
                )
        );
        var user = userRepository.findByUsername(authDTO.getUsername()).orElseThrow();
        var token = jwtService.generateToken(user);
        response.setToken(token);
        return response;
    }

    @Override
    public AuthenticationResponse logout() {
        return null;
    }
}
