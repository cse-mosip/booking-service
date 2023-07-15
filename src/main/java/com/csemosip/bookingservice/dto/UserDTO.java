package com.csemosip.bookingservice.dto;

import com.csemosip.bookingservice.model.utils.Role;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name ="role")
    private Role role;
}
