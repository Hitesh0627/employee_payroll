package com.example.employee_payroll.service;

import com.example.employee_payroll.dto.EmployeeDto;
import com.example.employee_payroll.model.Employee;

import java.util.List;

public interface IEmployeeService {
    Employee createEmployee(EmployeeDto employeeDTO);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Long id, EmployeeDto employeeDTO);
    void deleteEmployee(Long id);
}
