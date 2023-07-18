package com.csemosip.bookingservice.service;

import com.csemosip.bookingservice.dto.UserDTO;
import com.csemosip.bookingservice.model.User;


public interface UserService {
    User createUser(UserDTO userDTO) ;

    User findUser(String id);

    User findByUsername(String username);

}
