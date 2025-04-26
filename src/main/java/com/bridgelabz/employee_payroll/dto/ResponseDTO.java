package com.bridgelabz.employee_payroll.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

public @Data class ResponseDTO {
    private String message;
    private HttpStatus status;

    public ResponseDTO(String message, HttpStatus status) {
        this.message = message;
        this.status=status;
    }



}