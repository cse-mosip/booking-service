package com.csemosip.bookingservice.service.Impl;


import com.csemosip.bookingservice.dto.AuthDTO;
import com.csemosip.bookingservice.dto.AuthenticationResponse;

public interface AuthenticationServiceImpl {
    AuthenticationResponse login(AuthDTO authDTO);
}
