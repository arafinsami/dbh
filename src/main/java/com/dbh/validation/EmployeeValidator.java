package com.dbh.validation;

import com.dbh.dto.request.EmployeeRequest;
import com.dbh.entity.Employee;
import com.dbh.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static com.dbh.utils.Constants.ALREADY_EXIST;
import static com.dbh.utils.StringUtils.nonNull;

@Component
@RequiredArgsConstructor
public class EmployeeValidator implements Validator {

    private final EmployeeService employeeService;

    @Override
    public boolean supports(Class<?> clazz) {
        return EmployeeRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeRequest request = (EmployeeRequest) target;
        Employee employee = employeeService.findByEmail(request.getEmail()).get();
        if (nonNull(employee)) {
            errors.reject("email", null, ALREADY_EXIST);
        }
    }
}
