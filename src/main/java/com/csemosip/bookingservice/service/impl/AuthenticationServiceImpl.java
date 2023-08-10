package com.csemosip.bookingservice.service.impl;

import com.csemosip.bookingservice.dto.AuthDTO;
import com.csemosip.bookingservice.dto.AuthenticationResponse;
import com.csemosip.bookingservice.dto.VerificationResponse;
import com.csemosip.bookingservice.repository.UserRepository;
import com.csemosip.bookingservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Override
    public AuthenticationResponse login(AuthDTO authDTO) {
        AuthenticationResponse response = new AuthenticationResponse();
        try {

            // Call the external authentication endpoint
            // Once authenticated externally, can proceed to generate JWT token

            String username = authDTO.getUsername();
            String password = authDTO.getPassword();
            RestTemplate restTemplate = new RestTemplate();
            //TODO: Replace with the correct endpoint
            String verificationEndPoint = "https://36811170-f4c5-404c-90ad-3d807b36f503.mock.pstmn.io/api/public/verify";
            String requestPayload = "{\"email\": \""+username+"\" , \"password\": \""+password+"\"}";

            VerificationResponse verificationResponse = restTemplate.
                    postForObject(verificationEndPoint, requestPayload, VerificationResponse.class);

            if(verificationResponse.isVerified()){
                var user = userRepository.findByUsername(authDTO.getUsername()).orElseThrow();
                var token = jwtService.generateToken(user);
                response.setToken(token);
            }
            else {
                response.setToken("VERIFICATION FAILED");
            }
        }
        catch (AuthenticationException exception){
            response.setToken("CANNOT GENERATE");
        }
        return response;
    }

    @Override
    public AuthenticationResponse logout() {
        return null;
    }
}
