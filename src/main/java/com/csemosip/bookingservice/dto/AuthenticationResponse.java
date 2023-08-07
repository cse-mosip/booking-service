package com.csemosip.bookingservice.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String token;
    private boolean successStatus;
}
