package com.csemosip.bookingservice.controller;


import com.csemosip.bookingservice.dto.UserDTO;
import com.csemosip.bookingservice.model.User;
import com.csemosip.bookingservice.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController{
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findUser(@PathVariable Integer id) {
        User user = userService.findUser(id);
        return sendSuccessResponse(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDTO userDTO) {
        User user = userService.createUser(userDTO);
        return sendSuccessResponse(user, HttpStatus.CREATED);
    }
}
