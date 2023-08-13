package com.csemosip.bookingservice.service.impl;

import com.csemosip.bookingservice.dto.AuthDTO;
import com.csemosip.bookingservice.dto.AuthenticationResponse;
import com.csemosip.bookingservice.dto.VerificationResponse;
import com.csemosip.bookingservice.model.User;
import com.csemosip.bookingservice.model.utils.Role;
import com.csemosip.bookingservice.repository.UserRepository;
import com.csemosip.bookingservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Value("${mosip-user-verification-url}")
    String verificationEndPoint;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;

    @Override
    public AuthenticationResponse login(AuthDTO authDTO) {
        AuthenticationResponse response = new AuthenticationResponse();
        try {
            String username = authDTO.getUsername();
            String password = authDTO.getPassword();

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            Map<String, Object> map = new HashMap<>();
            map.put("email", username);
            map.put("password", password);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

            VerificationResponse verificationResponse = restTemplate.postForObject(
                    verificationEndPoint,
                    entity,
                    VerificationResponse.class
            );

            if (verificationResponse.isVerified()) {
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
