package com.bridgelabz.employee_payroll.service;

import com.bridgelabz.employee_payroll.dto.LoginRequestDTO;
import com.bridgelabz.employee_payroll.dto.ResponseDTO;
import com.bridgelabz.employee_payroll.dto.UserResponse;
import com.bridgelabz.employee_payroll.model.User;
import com.bridgelabz.employee_payroll.repository.UserRepository;
import com.bridgelabz.employee_payroll.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    EmailService emailService;

    public ResponseDTO register(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseDTO("User Registered successfully", HttpStatus.CREATED);
    }
    public ResponseDTO login(LoginRequestDTO loginRequest){
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());

        if (optionalUser.isEmpty()) {
            return new ResponseDTO("User not found", HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        boolean isPasswordValid = bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!isPasswordValid) {
            return new ResponseDTO("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        String jwtToken = jwtUtility.generateJwt(user.getEmail());
        user.setToken(jwtToken);
        userRepository.save(user);
        return new ResponseDTO("Login successful and token generated", HttpStatus.OK);

    }
    public UserResponse forgotPassword(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if(userOpt.isEmpty()){
            return new UserResponse("user not found",null, HttpStatus.BAD_REQUEST);
        }

        User user = userOpt.get();

        String code = String.format("%06d",new Random().nextInt(999999));
        user.setResetCode(code);
        user.setResetCodeExpiry(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);

        emailService.sendEmail(email,"Your password reset code","Your reset code is: "+code);

        return new UserResponse("Otp sent", user, HttpStatus.OK);
    }

    public UserResponse resetPassword(String email, String code, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isEmpty()){
            return new UserResponse("User not found",null, HttpStatus.NOT_FOUND);
        }

        User user = userOpt.get();

        if(user.getResetCode()==null || !user.getResetCode().equals(code)){
            return new UserResponse("reset code wrong",null, HttpStatus.CONFLICT);
        }

        if(user.getResetCodeExpiry().isBefore(LocalDateTime.now())){
            return new UserResponse("reset code expired",null, HttpStatus.CONFLICT);
        }

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        user.setResetCode(null);
        user.setResetCodeExpiry(null);
        userRepository.save(user);

        return new UserResponse("password reset successfully", user, HttpStatus.OK);
    }
}