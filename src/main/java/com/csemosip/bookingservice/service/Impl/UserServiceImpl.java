package com.csemosip.bookingservice.service.Impl;

import com.csemosip.bookingservice.dto.UserDTO;
import com.csemosip.bookingservice.exception.ResourceNotFoundException;
import com.csemosip.bookingservice.model.User;
import com.csemosip.bookingservice.repository.UserRepository;
import com.csemosip.bookingservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User createUser(UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        User user = modelMapper.map(userDTO, User.class);
        return userRepository.save(user);
    }

    @Override
    public User findUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }
}
