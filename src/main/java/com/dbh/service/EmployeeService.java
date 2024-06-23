package com.dbh.service;

import com.dbh.dto.projection.EmployeeProjection;
import com.dbh.entity.Employee;
import com.dbh.exception.ResourceNotFoundException;
import com.dbh.mapper.EmployeeMapper;
import com.dbh.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EntityManager entityManager;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(null);
        employeeRepository.delete(employee);
    }

    @Transactional(readOnly = true)
    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Employee findByEmployeeId(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return employee;
    }

    /*public Page<Employee> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable);
    }*/

    //DTO based projection
    /*@Transactional(readOnly = true)
    public List<EmployeeProjection> findAll() {
        return entityManager.createQuery("select " +
                        "new com.dbh.dto.projection.EmployeeProjection(e.id, e.email) from Employee e",
                EmployeeProjection.class).getResultList();
    }*/

    // Using RestTemplate
    /*@Transactional(readOnly = true)
    public List<Employee> findAll() throws JsonProcessingException {
        String queryURL = "http://localhost:9999/dbh-query-api/dbh-employee-query";
        ResponseEntity<String> response = restTemplate.getForEntity(queryURL, String.class);
        return objectMapper.readValue(response.getBody(), new TypeReference<>() {});
    }*/

    @Transactional(readOnly = true)
    public List<Employee> findAll() throws JsonProcessingException {
        String queryURL = "http://localhost:9999/dbh-query-api/dbh-employee-query";
        WebClient webClient = WebClient.builder().build();
        Mono<String> response = webClient.get()
                .uri(queryURL)
                .retrieve()
                .bodyToMono(String.class);
        String responseString = response.block();
        return objectMapper.readValue(responseString, new TypeReference<>() {});
    }

    /*@Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }*/
}