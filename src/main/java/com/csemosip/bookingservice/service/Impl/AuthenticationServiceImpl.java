package com.csemosip.bookingservice.service.Impl;


import com.csemosip.bookingservice.dto.AuthDTO;
import com.csemosip.bookingservice.model.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface AuthenticationServiceImpl {
    AuthenticationResponse login(AuthDTO authDTO);
    AuthenticationResponse logout();
}
