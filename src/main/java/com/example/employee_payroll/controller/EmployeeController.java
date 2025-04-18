package com.example.employee_payroll.controller;

import com.example.employee_payroll.dto.EmployeeDto;
import com.example.employee_payroll.dto.ResponseDto;
import com.example.employee_payroll.model.Employee;
import com.example.employee_payroll.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController            //it will return JSON responses.
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired   // call service methods(spring injects)
    private EmployeeService employeeService;   // contains business logic

    @PostMapping    //Handles POST requests to /employees.
    public ResponseDto createEmployee(@RequestBody EmployeeDto employeeDTO) { // @RequestBody EmployeeDto: Converts incoming JSON to EmployeeDto object.
        Employee employee = employeeService.createEmployee(employeeDTO);  //Calls service method createEmployee(...) to save the data.
        return new ResponseDto("Employee Created Successfully", employee);
    }

    @GetMapping
    public ResponseDto getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseDto("List of All Employees", employees);
    }

    @GetMapping("/{id}")
    public ResponseDto getEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseDto("Employee Found Successfully", employee);
    }

    @PutMapping("/{id}")
    public ResponseDto updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDTO) {
        Employee employee = employeeService.updateEmployee(id, employeeDTO);
        return new ResponseDto("Employee Updated Successfully", employee);
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseDto("Employee Deleted Successfully", null);
    }

}