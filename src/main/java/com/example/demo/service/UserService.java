package com.example.demo.service;

import com.example.demo.model.LibraryUser;
import com.example.demo.model.Login;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

// Class UserService manage methods about LibraryUser table in database.
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<LibraryUser> getUserById(String username){
        return userRepository.findByUsername(username);
    }

    //Add new record in LibraryUser table
    public String registerUser(LibraryUser user){

        String tempUsername =user.getUsername();
        Optional <LibraryUser> opUser= userRepository.findByUsername(tempUsername);
        //if username exists, stop the process
        if (opUser.isPresent()){
            return "This username already exists. Please try a different one.";
        }
        userRepository.save(user);
        return "Registration progress completed successfully.";
    }

    //if usernanme and password match, return role
    public String loginUser(Login request){
        Optional<LibraryUser> opUser =userRepository.findByUsername(request.getUsername());
        if (opUser.isPresent()){
            if(Objects.equals(opUser.get().getPassword(), request.getPassword())){
                if(opUser.get().getRole()==1){
                    return "ROLE_USER";
                }
                return "ROLE_ADMIN";
            }
        }
        return "None";
    }
}
