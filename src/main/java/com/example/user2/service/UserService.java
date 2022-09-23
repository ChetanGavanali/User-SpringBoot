package com.example.user2.service;

import com.example.user2.dto.LoginDTO;
import com.example.user2.dto.UserDTO;
import com.example.user2.exception.UserException;
import com.example.user2.model.User;
import com.example.user2.repo.UserRepo;
import com.example.user2.util.EmailSenderService;
import com.example.user2.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements ServiceInterface {
    @Autowired
    UserRepo userRepo;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    EmailSenderService emailSender;

    @Override
    public String insertRecord(UserDTO userDto) throws UserException {
        User user = new User(userDto);
        userRepo.save(user);
        String token = tokenUtil.createToken(user.getUserId());
        emailSender.sendEmail(user.getEmail(), "Added Your Details", "http://localhost:8080/user/retrieve/" + token);
        return token;
    }



    @Override
    public User FindById(int id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent())
        return user.get();
        else {
            throw new UserException("id is not found");
        }

    }



    @Override
    public User editByEmail(UserDTO userDTO, String email) {
        User editdata = (User) userRepo.findByEmail(email);
        if (editdata != null) {
            editdata.setFirstName(userDTO.getFirstName());
            editdata.setLastName(userDTO.getLastName());
            editdata.setEmail(userDTO.getEmail());
            editdata.setAddress(userDTO.getAddress());
            editdata.setDOB(userDTO.getDOB());
            editdata.setPassword(userDTO.getPassword());
            User user = userRepo.save(editdata);
            String token = tokenUtil.createToken(editdata.getUserId());
            emailSender.sendEmail(editdata.getEmail(), "Added Your Details/n", "http://localhost:8080/user/retrieve/" + token);
            return user;
        } else {
            throw new UserException("email:" + email + " is not present ");
        }

    }

    @Override
    public User getDataByToken(String token) {
        int Userid = tokenUtil.decodeToken(token);
        Optional<User> existingData = userRepo.findById((Userid));
        if (existingData.isPresent()) {
            return existingData.get();
        } else
            throw new UserException("Invalid Token");
    }
    @Override
    public User findAll() {
        List<User> user = userRepo.findAll();
        return user.get(0);

    }

    @Override
    public User loginUser(LoginDTO loginDTO) {
        Optional<User> userDetails = Optional.ofNullable(userRepo.findByEmail(loginDTO.getEmail()));
        if (userDetails.isPresent()) {
            if (userDetails.get().getPassword().equals(loginDTO.getPassword())) {
                emailSender.sendEmail(userDetails.get().getEmail(), "About Login", "Login Successful!");
                return userDetails.get();
            } else
                emailSender.sendEmail(userDetails.get().getEmail(), "About Login", "Invalid password!");
            throw new UserException("Wrong Password!");
        } else
            throw new UserException("Login Failed, Wrong email or password!!!");
    }

    @Override
    public String forgotPassword(String email) {
        User editdata = userRepo.findByEmail(email);
        if (editdata != null){
            emailSender.sendEmail(editdata.getEmail(), "About Login", "token");
            return "Reset link send successfully";
        }else
            throw new UserException("Login Failed, Wrong email or password!!!");
    }

    @Override
    public String resetPassword(LoginDTO loginDTO) {
        Optional<User> userDetails = Optional.ofNullable(userRepo.findByEmail(loginDTO.getEmail()));
        String password = loginDTO.getPassword();
        if(userDetails.isPresent()){
            userDetails.get().setPassword(password);
            userRepo.save(userDetails.get());
            return "Password Changed";
        }else
            return "Invalid Email Address";
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepo.findByEmail(email);
        return user;
    }

}