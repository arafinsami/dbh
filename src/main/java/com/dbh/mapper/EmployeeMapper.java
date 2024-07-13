package com.dbh.mapper;

import com.dbh.dto.request.EmployeeRequest;
import com.dbh.dto.response.EmployeeResponse;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeResponse from(EmployeeRequest employee) {   // manual bean conversion
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setEmail(employee.getEmail());
        return response;
    }

    public void update(EmployeeRequest employee, EmployeeRequest request) {
        employee.setId(request.getId());
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPassword(request.getPassword());
    }
}
