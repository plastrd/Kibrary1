package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

//Class for users and admin
@Entity
public class LibraryUser {
    @Id
    private String username;
    @NotNull
    @Column(nullable = false)
    private String name;
    @NotNull
    @Column(nullable = false)
    private String surname;
    @NotNull
    @Column(nullable = false)
    private String password;

    // user code:1, admin code:0
    @NotNull
    @Column(nullable = false)
    private Integer role;

    public LibraryUser(){}

    public LibraryUser(String name,String surname,String username,String password,int role){
        this.name=name;
        this.surname=surname;
        this.username=username;
        this.password=password;
        if (role==0) {
            this.role = 0;
        }else{
            // for the situation role==2, system save User_role.
            this.role=1;
        }
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public int getRole() {
        return role;
    }
}
