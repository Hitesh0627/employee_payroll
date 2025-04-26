package com.bridgelabz.employee_payroll.service;

import com.bridgelabz.employee_payroll.dto.EmployeeDTO;
import com.bridgelabz.employee_payroll.dto.ResponseDTO;
import com.bridgelabz.employee_payroll.model.EmployeeInfo;

import java.util.List;

public interface IEmployeeService {

    public ResponseDTO deleteEmployee(Long id);

    public ResponseDTO updateUser(Long id, EmployeeDTO empDTO);

    public List<EmployeeInfo> displayUser();

    public EmployeeInfo getUserByID(Long id);


    public ResponseDTO createUser(EmployeeDTO emp);
}