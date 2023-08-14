package com.csemosip.bookingservice.controller;


import com.csemosip.bookingservice.dto.UserDTO;
import com.csemosip.bookingservice.exception.ResourceNotFoundException;
import com.csemosip.bookingservice.model.User;
import com.csemosip.bookingservice.model.utils.Role;
import com.csemosip.bookingservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> findUser(@PathVariable String id) {
        User user;
        try {
            user = userService.findUser(id);
        } catch (ResourceNotFoundException e) {
            user = new User();
            user.setUsername(id);
            user.setRole(Role.RESOURCE_USER);
        }
        return sendSuccessResponse(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDTO userDTO) {
        User user = userService.createUser(userDTO);
        return sendSuccessResponse(user, HttpStatus.OK);
    }
}
