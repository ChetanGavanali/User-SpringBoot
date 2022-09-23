package com.example.user2.service;

import com.example.user2.dto.LoginDTO;
import com.example.user2.dto.UserDTO;
import com.example.user2.model.User;

public interface ServiceInterface {

    String insertRecord(UserDTO userDTO);


    User FindById(int id);

    User editByEmail(UserDTO userDTO, String email_address);

    User getDataByToken(String token);

    User findAll();

    User loginUser(LoginDTO loginDTO);

    String forgotPassword(String email);

    String resetPassword(LoginDTO loginDto);

    User findByEmail(String email);
}
