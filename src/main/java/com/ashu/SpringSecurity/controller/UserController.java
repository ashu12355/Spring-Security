package com.ashu.SpringSecurity.controller;

import com.ashu.SpringSecurity.entity.RegisterUserRequest;
import com.ashu.SpringSecurity.entity.RegisterUserResponse;
import com.ashu.SpringSecurity.entity.Role;
import com.ashu.SpringSecurity.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        registerUserRequest.setRole(Role.USER);
        RegisterUserResponse userResponse =  userService.registerUser(registerUserRequest);
        return ResponseEntity.ok(userResponse);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create")
    public ResponseEntity<RegisterUserResponse> registerByAdmin(@RequestBody RegisterUserRequest registerUserRequest){
        RegisterUserResponse userResponse = userService.registerUser(registerUserRequest);
        return ResponseEntity.ok(userResponse);
    }
}
