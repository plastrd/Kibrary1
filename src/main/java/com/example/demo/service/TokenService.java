package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.Objects;

//Token is the key to unlock admin 's actions.
@Service
public class TokenService {

    //return token
    public String getToken(String role){
        if (Objects.equals(role, "ROLE_USER")){
            return "1234user";
        }
        return "0982admin";
    }

    //check if token match
    public boolean authorize_Admin(String token){
        if(Objects.equals(token, "0982admin")){
            return true;
        }
        return false;
    }
}
