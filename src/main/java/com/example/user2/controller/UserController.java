package com.example.user2.controller;

import com.example.user2.dto.LoginDTO;
import com.example.user2.dto.ResponseDTO;
import com.example.user2.dto.UserDTO;
import com.example.user2.model.User;
import com.example.user2.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    ServiceInterface serviceInterface;

    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> AddUserDetails(@Valid @RequestBody UserDTO userDTO) {
        String details = serviceInterface.insertRecord(userDTO);
        ResponseDTO responseDTO = new ResponseDTO("Data Added successfully", details);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<ResponseDTO> findAllUser() {
        User response = serviceInterface.findAll();
        ResponseDTO responseDTO = new ResponseDTO("All User List", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/get/{Id}")
    public ResponseEntity<ResponseDTO> FindById(@PathVariable int Id) {
        User response = serviceInterface.FindById(Id);
        ResponseDTO responseDto = new ResponseDTO("All Details of Person using Id", response);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseDTO> getDataByemail(@PathVariable String email) {
        User response = serviceInterface.findByEmail(email);
        ResponseDTO respDTO = new ResponseDTO("Data by using email", response);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }


    @PutMapping("/edit/{email}")
    public ResponseEntity<ResponseDTO> updateById(@Valid @RequestBody UserDTO userDTO, @PathVariable String email) {
        User response = serviceInterface.editByEmail(userDTO, email);
        ResponseDTO respDTO = new ResponseDTO("Person details is updated", response);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }

    @GetMapping("/retrieve/{token}")
    public ResponseEntity<ResponseDTO> getUserDetails(@Valid @PathVariable String token) {
        User response = serviceInterface.getDataByToken(token);
        ResponseDTO respDTO = new ResponseDTO("Data retrieved successfully", response);
        return new ResponseEntity(respDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody LoginDTO loginDTO) {
        User response = serviceInterface.loginUser(loginDTO);
        ResponseDTO responseDTO = new ResponseDTO("Login Successful!", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/forgotpassword/{email}")
    public ResponseEntity<ResponseDTO> forgotPasswordByemail(@PathVariable String email) {
        String response = serviceInterface.forgotPassword(email);
        ResponseDTO respDTO = new ResponseDTO("Link sent successfully", response);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody LoginDTO loginDto) {
        String response = serviceInterface.resetPassword(loginDto);
        return new ResponseEntity(response, HttpStatus.OK);
    }


}