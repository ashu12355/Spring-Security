package com.ashu.SpringSecurity.service;

import com.ashu.SpringSecurity.entity.RegisterUserRequest;
import com.ashu.SpringSecurity.entity.RegisterUserResponse;
import com.ashu.SpringSecurity.entity.Users;
import com.ashu.SpringSecurity.repository.UserDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDetailsRepository userDetailsRepository,PasswordEncoder passwordEncoder){
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest){

        if(userDetailsRepository.findByUsername(registerUserRequest.getUsername()).isPresent()){
            throw new RuntimeException("User Already Exist");
        }
        Users user = new Users();
        user.setUsername(registerUserRequest.getUsername());
        user.setRole(registerUserRequest.getRole());
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        Users savedUser =  userDetailsRepository.save(user);
       return new RegisterUserResponse(savedUser.getId(),savedUser.getUsername(),savedUser.getRole());
    }
}
