package com.dbh.controller;

import com.dbh.dto.request.EmployeeRequest;
import com.dbh.dto.request.LoginRequestDTO;
import com.dbh.dto.request.SingUpRequestDTO;
import com.dbh.dto.response.EmployeeResponse;
import com.dbh.dto.response.LoginResponseDTO;
import com.dbh.mapper.EmployeeMapper;
import com.dbh.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<JSONObject> save(@Valid @RequestBody EmployeeRequest request,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                           BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        String token = authorization.replace("Bearer ", "");
        EmployeeRequest employee = employeeService.save(request);
        EmployeeResponse response = employeeMapper.from(employee);
        return new ResponseEntity<>(success(response).getJson(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<JSONObject> update(@Valid @RequestBody EmployeeRequest request,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                             BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }
        String token = authorization.replace("Bearer ", "");
        EmployeeRequest employee = employeeService.findByEmployeeId(request.getId(), token);
        employeeMapper.update(employee, request);
        EmployeeRequest updatedEmployee = employeeService.update(employee, token);
        EmployeeResponse response = employeeMapper.from(updatedEmployee);
        return new ResponseEntity<>(success((response)).getJson(), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "get all  employees")
    public ResponseEntity<JSONObject> findAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization)
            throws JsonProcessingException {
        String token = authorization.replace("Bearer ", "");
        List<EmployeeRequest> employees = employeeService.findAll(token);
        log.info("employees: {}", employees);
        return new ResponseEntity<>(success(employees).getJson(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "find an employee by id")
    public ResponseEntity<JSONObject> findById(@PathVariable Long id,
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization)
            throws JsonProcessingException {
        String token = authorization.replace("Bearer ", "");
        EmployeeRequest employee = employeeService.findByEmployeeId(id, token);
        return new ResponseEntity<>(success(employee).getJson(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete employee by id")
    public ResponseEntity<JSONObject> delete(@PathVariable Long id) throws JsonProcessingException {
        employeeService.delete(id);
        return new ResponseEntity<>(success("employee deleted by id: " + id).getJson(), HttpStatus.OK);
    }

    @PostMapping("login")
    @Operation(summary = "login an employee")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginDTO) throws JsonProcessingException {
        Object responseDTO = employeeService.login(loginDTO);
        ObjectMapper mapper = new ObjectMapper();
        LoginResponseDTO loginResponseDTO = mapper.convertValue(responseDTO, LoginResponseDTO.class);
        System.setProperty("token", loginResponseDTO.getToken());
        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

    @PostMapping("singup")
    @Operation(summary = "signup an employee")
    public ResponseEntity<Object> singup(@RequestBody SingUpRequestDTO singUp) throws JsonProcessingException {
        Object responseDTO = employeeService.signup(singUp);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}