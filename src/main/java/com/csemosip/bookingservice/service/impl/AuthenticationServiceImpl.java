package com.csemosip.bookingservice.service.impl;

import com.csemosip.bookingservice.dto.AuthDTO;
import com.csemosip.bookingservice.dto.AuthenticationResponse;
import com.csemosip.bookingservice.model.User;
import com.csemosip.bookingservice.model.utils.Role;
import com.csemosip.bookingservice.repository.UserRepository;
import com.csemosip.bookingservice.service.AuthenticationService;
import com.csemosip.bookingservice.utils.RegistrationServiceAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;

    @Autowired
    private RegistrationServiceAPI registrationServiceAPI;

    @Override
    public AuthenticationResponse login(AuthDTO authDTO) {
        AuthenticationResponse response = new AuthenticationResponse();
        try {
            String username = authDTO.getUsername();
            String password = authDTO.getPassword();

            boolean isValid = registrationServiceAPI.verifyUserCredentials(username, password);

            if (isValid) {
                User user;
                try {
                    user = userRepository.findByUsername(authDTO.getUsername()).orElseThrow();
                } catch (NoSuchElementException e) {
                    user = new User();
                    user.setUsername(username);
                    user.setRole(Role.RESOURCE_USER);
                }
                var token = jwtService.generateToken(user);
                response.setToken(token);
            } else {
                response.setToken("VERIFICATION FAILED");
            }
        } catch (AuthenticationException exception) {
            response.setToken("CANNOT GENERATE TOKEN");
        }
        return response;
    }

    @Override
    public AuthenticationResponse logout() {
        return null;
    }
}
