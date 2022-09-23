package com.example.user2.model;

import com.example.user2.dto.LoginDTO;
import com.example.user2.dto.UserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    int userId;
    String firstName;
    String lastName;
    String address;

    String email;
    LocalDate DOB;
    String password;

    public User(UserDTO userdto){
        this.firstName = userdto.getFirstName();
        this.lastName = userdto.getLastName();
        this.address = userdto.getAddress();
        this.email = userdto.getEmail();
        this.DOB = userdto.getDOB();
        this.password = userdto.getPassword();
    }
    public User(LoginDTO loginDTO){
        this.email= loginDTO.getEmail();
        this.password= loginDTO.getPassword();
    }
}