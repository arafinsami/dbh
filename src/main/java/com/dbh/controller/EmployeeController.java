package com.dbh.controller;

import com.dbh.dto.projection.EmployeeProjection;
import com.dbh.dto.request.EmployeeRequest;
import com.dbh.dto.response.EmployeeResponse;
import com.dbh.entity.Employee;
import com.dbh.mapper.EmployeeMapper;
import com.dbh.service.EmployeeService;
import com.dbh.validation.EmployeeValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.dbh.exception.ApiError.fieldError;
import static com.dbh.utils.ResponseBuilder.error;
import static com.dbh.utils.ResponseBuilder.success;
import static org.springframework.http.ResponseEntity.badRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Employee API")
@RequestMapping(path = "employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;

    private final EmployeeValidator employeeValidator;

    @PostMapping
    @Operation(summary = "save an employee")
    public ResponseEntity<JSONObject> save(@Valid @RequestBody EmployeeRequest request, BindingResult bindingResult) {
        ValidationUtils.invokeValidator(employeeValidator, request, bindingResult);
        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        Employee employee = employeeMapper.save(request);
        employeeService.save(employee);
        EmployeeResponse response = employeeMapper.from(employee);
        return new ResponseEntity<>(success(response).getJson(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<JSONObject> update(@Valid @RequestBody EmployeeRequest dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        Employee employee = employeeService.findById(dto.getId()).orElseThrow();
        employeeMapper.update(employee, dto);
        employeeService.save(employee);
        EmployeeResponse response = employeeMapper.from(employee);
        return new ResponseEntity<>(success((response)).getJson(), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "get all  employees")
    public ResponseEntity<JSONObject> findAll() throws JsonProcessingException {
        //List<EmployeeProjection> employeeResponses = employeeService.findAll();
        //List<Employee> employeeResponses = employeeService.findAll();
        //return new ResponseEntity<>(success(employeeResponses).getJson(), HttpStatus.OK);
        List<Employee> employees = employeeService.findAll();
        log.info("employees: {}", employees);
        return new ResponseEntity<>(success(employees).getJson(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "find an employee by id")
    public ResponseEntity<JSONObject> findById(@PathVariable Long id) {
        Employee employee = employeeService.findByEmployeeId(id);
        return new ResponseEntity<>(success(employee).getJson(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete employee by id")
    public ResponseEntity<JSONObject> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return new ResponseEntity<>(success("employee deleted by id: " + id).getJson(), HttpStatus.OK);
    }
}