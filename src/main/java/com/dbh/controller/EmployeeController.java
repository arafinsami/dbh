package com.dbh.controller;

import com.dbh.dto.request.EmployeeRequest;
import com.dbh.dto.response.EmployeeResponse;
import com.dbh.entity.Employee;
import com.dbh.mapper.EmployeeMapper;
import com.dbh.service.EmployeeService;
import com.dbh.utils.ResponseBuilder;
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
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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

    @PostMapping
    @Operation(summary = "save an employee")
    public Mono<ResponseEntity<JSONObject>> save(@Valid @RequestBody EmployeeRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Mono.just(badRequest().body(error(fieldError(bindingResult)).getJson()));
        }
        Employee employee = employeeMapper.save(request);
        return employeeService.save(employee).map(employeeResponse -> {
            EmployeeResponse response = employeeMapper.from(employeeResponse);
            return new ResponseEntity<>(success(response).getJson(), HttpStatus.OK);
        });
    }

    @PutMapping
    public Mono<ResponseEntity<JSONObject>> update(@Valid @RequestBody EmployeeRequest dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Mono.just(badRequest().body(error(fieldError(bindingResult)).getJson()));
        }
        return employeeService.findById(dto.getId()).flatMap(optionalEmployee -> {
            if (optionalEmployee.isPresent()) {
                Employee employee = optionalEmployee.get();
                employeeMapper.update(employee, dto);
                return employeeService.update(employee).map(employeeResponse -> {
                    EmployeeResponse response = employeeMapper.from(employeeResponse);
                    return new ResponseEntity<>(success(response).getJson(), HttpStatus.OK);
                });
            } else {
                return Mono.just(new ResponseEntity<>(error("Employee not found").getJson(), HttpStatus.NOT_FOUND));
            }
        });
    }

    @GetMapping
    @Operation(summary = "get all  employees")
    public Mono<ResponseEntity<JSONObject>> findAll() {
        return employeeService.findAll().collectList().map(employees -> {
            log.info("employees: {}", employees);
            return new ResponseEntity<>(success(employees).getJson(), HttpStatus.OK);
        });
    }

    @GetMapping("{id}")
    @Operation(summary = "find an employee by id")
    public Mono<ResponseEntity<JSONObject>> findById(@PathVariable Long id) {
        return employeeService.findByEmployeeId(id)
                .map(employee -> new ResponseEntity<>(ResponseBuilder.success(employee).getJson(), HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(error("Employee nof dound ").getJson(), HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete employee by id")
    public Mono<ResponseEntity<JSONObject>> delete(@PathVariable Long id) throws JsonProcessingException {
        return employeeService.delete(id)
                .thenReturn(new ResponseEntity<>(success("Employee deleted").getJson(), HttpStatus.OK));
    }
}