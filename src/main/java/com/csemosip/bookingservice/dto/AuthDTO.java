package com.csemosip.bookingservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthDTO {
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

}