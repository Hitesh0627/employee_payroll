package com.example.employee_payroll.dto;

public class EmployeeDto {

    private String name;
    private String salary;

    public EmployeeDto() {
    }

    public EmployeeDto(String name, String salary) {
        this.name = name;
        this.salary = salary;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{name='" + name + "', salary='" + salary + "'}";
    }
}

