package com.csemosip.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthDTO {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

}
