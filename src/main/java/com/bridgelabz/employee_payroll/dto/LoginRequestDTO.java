package com.bridgelabz.employee_payroll.dto;


import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}