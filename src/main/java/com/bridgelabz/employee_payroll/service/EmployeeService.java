package com.bridgelabz.employee_payroll.service;

import com.bridgelabz.employee_payroll.dto.EmployeeDTO;
import com.bridgelabz.employee_payroll.dto.ResponseDTO;
import com.bridgelabz.employee_payroll.model.EmployeeInfo;
import com.bridgelabz.employee_payroll.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeService implements IEmployeeService{
    @Autowired
    private EmployeeRepository employeerepository;

    // service deleting employee
    public ResponseDTO deleteEmployee(Long id){
        employeerepository.deleteById(id);
        return new ResponseDTO("User deleted successfully", HttpStatus.OK);
    }

    // service updating employee
    public ResponseDTO updateUser(Long id, EmployeeDTO empDTO) {
        Optional<EmployeeInfo> employee = employeerepository.findById(id);

        if (employee.isEmpty()) {
            log.warn("Update failed. No employee found with ID: {}", id);
            return new ResponseDTO("No such user exists", HttpStatus.BAD_REQUEST);
        }

        EmployeeInfo emp = employee.get();

        if (empDTO.getName() != null) {
            log.debug("Updating name for employee ID {}: {}", id, empDTO.getName());
            emp.setEmployeeName(empDTO.getName());
        }

        if (empDTO.getSalary() != null && !empDTO.getSalary().isNaN()) {
            log.debug("Updating salary for employee ID {}: {}", id, empDTO.getSalary());
            emp.setSalary(empDTO.getSalary());
        }

        if (empDTO.getGender() != null) {
            log.debug("Updating gender for employee ID {}: {}", id, empDTO.getGender());
            emp.setGender(empDTO.getGender());
        }

        if (empDTO.getStartDate() != null) {
            log.debug("Updating start date for employee ID {}: {}", id, empDTO.getStartDate());
            emp.setStartDate(empDTO.getStartDate());
        }

        if (empDTO.getProfilePic() != null) {
            log.debug("Updating profile picture for employee ID {}: {}", id, empDTO.getProfilePic());
            emp.setProfilePic(empDTO.getProfilePic());
        }

        if (empDTO.getDepartments() != null && !empDTO.getDepartments().isEmpty()) {
            log.debug("Updating departments for employee ID {}: {}", id, empDTO.getDepartments());
            emp.setDepartments(empDTO.getDepartments());
        }

        employeerepository.save(emp);
        log.info("Successfully updated employee with ID: {}", id);
        return new ResponseDTO("User updated successfully", HttpStatus.OK);
    }


    // service display employee
    public List<EmployeeInfo> displayUser(){
        log.info("Fetching all employee records from database");
        return (employeerepository.findAll());
    }


    // service fetch employee by ID
    public EmployeeInfo getUserByID(Long id){
        log.info("Fetching employee by ID: {}", id);
        Optional<EmployeeInfo> employee=employeerepository.findById(id);

        if (employee.isEmpty()) {
            log.error("No employee found with ID: {}", id);
            throw new RuntimeException("Employee not found");
        }
        EmployeeInfo emp= employee.get();
        log.info("Employee found with ID: {}", id);
        return emp;
    }


    // service create employee
    public ResponseDTO  createUser(EmployeeDTO employee){
        EmployeeInfo emp = new EmployeeInfo();
        emp.setEmployeeName(employee.getName());
        emp.setSalary(employee.getSalary());
        emp.setGender(employee.getGender());
        emp.setDepartments(employee.getDepartments());
        emp.setStartDate(employee.getStartDate());
        emp.setEmail(employee.getEmail());
        log.info(String.valueOf(employee.getStartDate()));
        emp.setProfilePic(employee.getProfilePic());
        log.info("Creating new employee: {}", emp);
        employeerepository.save(emp);
        log.info("Employee created successfully: {}", emp.getEmployeeName());
        return new ResponseDTO("User created successfully", HttpStatus.CREATED);
    }
}