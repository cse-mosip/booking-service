package com.csemosip.bookingservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class AbstractController {
    public <T> ResponseEntity<Map<String, Object>> sendSuccessResponse(T data, HttpStatus httpStatus) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("status", httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }
}
