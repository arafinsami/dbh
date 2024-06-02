package com.dbh.controller;

import com.dbh.dto.request.EmployeeRequest;
import com.dbh.dto.response.EmployeeResponse;
import com.dbh.entity.Employee;
import com.dbh.mapper.EmployeeMapper;
import com.dbh.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Employee API")
@RequestMapping(path = "employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;

    @PostMapping
    @Operation(summary = "save an employee")
    public ResponseEntity<?> save(@RequestBody EmployeeRequest dto) {
        Employee employee = employeeMapper.save(dto);
        employeeService.save(employee);
        EmployeeResponse response = employeeMapper.from(employee);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody EmployeeRequest dto) {
        Employee employee = employeeService.findById(dto.getId()).orElseThrow();
        employeeMapper.update(employee, dto);
        employeeService.save(employee);
        EmployeeResponse response = employeeMapper.from(employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "get all  employees")
    public ResponseEntity<?> findAll() {
        List<EmployeeResponse> employeeResponses = employeeService.findAll().stream().map(employeeMapper::from).collect(Collectors.toList());
        return new ResponseEntity<>(employeeResponses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete employee by id")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return new ResponseEntity<>("employee deleted by id: " + id, HttpStatus.OK);
    }
}