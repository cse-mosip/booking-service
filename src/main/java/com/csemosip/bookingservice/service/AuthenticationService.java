package com.csemosip.bookingservice.service;


import com.csemosip.bookingservice.dto.AuthDTO;
import com.csemosip.bookingservice.dto.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse login(AuthDTO authDTO);
    AuthenticationResponse logout();
}
