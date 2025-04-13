package com.example.demo.controller;

import com.example.demo.model.LibraryUser;
import com.example.demo.model.Login;

import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

//Controller for  registration and login
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    public UserController(UserService userService,TokenService tokenService) {
        this.userService = userService;
        this.tokenService=tokenService;
    }

    //http://localhost:8080/api/user/registration + jsonbody
    // Creates account
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody LibraryUser user){
        return ResponseEntity.ok(userService.registerUser(user));
    }

    //http://localhost:8080/api/user/login + json-body
    // Make login.
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login request){
        // check the username-password and return thr role. Not match return None.
        String  role=userService.loginUser(request);
        if(Objects.equals(role, "None")){
            return ResponseEntity.ok("Wrong username/password.");
        }
        //return token for specific role
        String token=tokenService.getToken(role);
        return ResponseEntity.ok(Map.of(
                "Message", "Successful Login.",
                "token",token,
                "role",role));
    }

}
