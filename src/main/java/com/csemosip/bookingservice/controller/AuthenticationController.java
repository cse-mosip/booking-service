package com.csemosip.bookingservice.controller;


import com.csemosip.bookingservice.dto.AuthDTO;
import com.csemosip.bookingservice.dto.AuthenticationResponse;
import com.csemosip.bookingservice.service.Impl.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController extends AbstractController{
    @Autowired
    AuthenticationServiceImpl authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>>  authenticate(@RequestBody AuthDTO authDTO) {
        AuthenticationResponse auth = authenticationService.login(authDTO);
        return sendSuccessResponse(auth, HttpStatus.ACCEPTED);
    }
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        return sendSuccessResponse("success", HttpStatus.ACCEPTED);
    }
}
