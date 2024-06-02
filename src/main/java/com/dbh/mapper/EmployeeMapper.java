package com.dbh.mapper;

import com.dbh.dto.request.EmployeeRequest;
import com.dbh.dto.response.EmployeeResponse;
import com.dbh.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeResponse from(Employee employee) {   // manual bean conversion
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setEmail(employee.getEmail());
        return response;
    }

    public void update(Employee employee, EmployeeRequest dto) {
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPassword(dto.getPassword());
    }

    public Employee save(EmployeeRequest dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        return employee;
    }
}
