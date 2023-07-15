package com.csemosip.bookingservice.service.Impl;


import com.csemosip.bookingservice.dto.AuthDTO;
import com.csemosip.bookingservice.model.AuthenticationResponse;

public interface AuthenticationServiceImpl {
    AuthenticationResponse login(AuthDTO authDTO);
    AuthenticationResponse logout();
}
