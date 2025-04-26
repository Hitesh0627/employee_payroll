package com.bridgelabz.employee_payroll.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.processing.Pattern;

import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeDTO {
    @NotBlank(message="name is required")
   // @Pattern(regexp = "^[A-Z]{1}[A-Za-z\\s]{2,}$", message = "Name must Start with capital letters and should not contain a number")
    private String name;

    @Min(value = 10000, message = "salary should not be less than Rs.100000")
    private Double salary;

    @NotBlank(message="Gender is required")
//    @Pattern(regexp = "male|female", message = "gender needs to be male or female")
    private String gender;

    private String email;

    @NotNull(message = "Date should not be null")
    @JsonFormat(shape=JsonFormat.Shape.STRING , pattern = "dd MMM yyyy", locale = "en")
    private LocalDate startDate;

    @NotBlank(message = "profile pic can not be empty")
    private String profilePic;

    @NotNull(message = "department can not be null")
    @Size(min = 1, message = "At least one department must be specified")
    private List<String> departments;

    public EmployeeDTO(String name, Double salary, String gender, LocalDate startDate, String profilePic, List<String> departments) {
        this.name = name;
        this.salary = salary;
        this.gender=gender;
        this.startDate=startDate;
        this.profilePic=profilePic;
        this.departments=departments;
    }


}