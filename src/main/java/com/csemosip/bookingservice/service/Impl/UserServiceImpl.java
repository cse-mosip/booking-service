package com.csemosip.bookingservice.service.Impl;

import com.csemosip.bookingservice.dto.UserDTO;
import com.csemosip.bookingservice.model.User;


public interface UserServiceImpl {
    User createUser(UserDTO userDTO) ;

    User findUser(String id);

    User findByUsername(String username);

}
