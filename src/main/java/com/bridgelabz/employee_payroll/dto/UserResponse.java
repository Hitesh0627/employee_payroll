package com.bridgelabz.employee_payroll.dto;

import com.bridgelabz.employee_payroll.model.User;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserResponse {
    private String message;
    private HttpStatus status;
    private User user;

    public UserResponse(String message, User user, HttpStatus status) {
        this.message = message;
        this.status=status;
        this.user=user;
    }
}