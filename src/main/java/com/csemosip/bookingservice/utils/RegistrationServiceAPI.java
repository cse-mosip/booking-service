package com.csemosip.bookingservice.utils;

import com.csemosip.bookingservice.dto.VerificationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrationServiceAPI {
    @Value("${mosip-registration-service-url}")
    String mosipRegistrationServiceUrl;
    @Autowired
    private RestTemplate restTemplate;

    public boolean verifyUserCredentials(String email, String password) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        VerificationResponse verificationResponse = restTemplate.postForObject(
                mosipRegistrationServiceUrl + "/api/public/verify",
                entity,
                VerificationResponse.class
        );

        return verificationResponse.isValid();
    }

    public String getStudentEmail(String index) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String[] indices = {index};
        String[] fields = {"index", "email"};

        Map<String, Object> authRequestBody = Map.ofEntries(
                Map.entry("indices", indices),
                Map.entry("fields", fields)
        );
        HttpEntity<Object> request = new HttpEntity<>(authRequestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                mosipRegistrationServiceUrl + "/api/public/data",
                request,
                String.class
        );

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Invalid user data.");
        }

        JsonNode jsonNode;
        try {
            jsonNode = new ObjectMapper().readTree(responseEntity.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonNode.get(0).get("email").asText();
    }
}
