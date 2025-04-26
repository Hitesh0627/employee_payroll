package com.bridgelabz.employee_payroll.controller;

import com.bridgelabz.employee_payroll.dto.EmployeeDTO;
import com.bridgelabz.employee_payroll.dto.LoginRequestDTO;
import com.bridgelabz.employee_payroll.dto.ResponseDTO;
import com.bridgelabz.employee_payroll.dto.UserResponse;
import com.bridgelabz.employee_payroll.model.EmployeeInfo;
import com.bridgelabz.employee_payroll.model.User;
import com.bridgelabz.employee_payroll.service.EmployeeService;
import com.bridgelabz.employee_payroll.service.UserService;
import com.bridgelabz.employee_payroll.util.JwtUtility;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/employee-payroll-service")
@Validated
public class EmployeeController {


    // Injecting Service class
    @Autowired
    private EmployeeService employeeservice;

    @Autowired
    private UserService userService;



    // Update Employee using employeeID
    @PutMapping("/update/{id}")
    public ResponseDTO updateUser(@PathVariable(value="id") Long id,@Valid @RequestBody EmployeeDTO emp){
        return employeeservice.updateUser(id,emp);
    }

    // Display all employees
    @GetMapping("/display")
    public List<EmployeeInfo> displayUser(){
        return employeeservice.displayUser();
    }

    // Get Employee by employeeID
    @GetMapping("/getByID/{id}")
    public EmployeeInfo getUser(@PathVariable(value="id") Long id){
        return employeeservice.getUserByID(id);
    }
    // Create Employee using post request
    @PostMapping("/post")
    public ResponseDTO post(@Valid @RequestBody EmployeeDTO employee){

        return employeeservice.createUser(employee);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteEmployee(@PathVariable(value="id") Long id){
        return employeeservice.deleteEmployee(id);

    }

    @PostMapping("/register")
    public ResponseDTO registerUser(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseDTO loginUser(@RequestBody LoginRequestDTO loginRequest) {
        return userService.login(loginRequest);



    }
    @PostMapping("/reset-password")
    public UserResponse reset(@RequestParam(value="email") String email, @RequestParam(value = "code") String code, @RequestParam(value = "newPassword") String newPassword){
        return userService.resetPassword(email,code,newPassword);
    }

    @PostMapping("/forgot-password")
    public UserResponse forgot(@RequestParam(value = "email") String email){
        return userService.forgotPassword(email);
    }
}