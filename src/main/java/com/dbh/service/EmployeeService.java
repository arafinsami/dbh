package com.dbh.service;

import com.dbh.entity.Employee;
import com.dbh.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ObjectMapper objectMapper;

    @Transactional
    public Mono<Employee> save(Employee employee) {
        String queryURL = "http://localhost:9999/dbh-command-api/dbh-employee-command";
        WebClient webClient = WebClient.builder().build();
        return webClient
                .post()
                .uri(queryURL)
                .bodyValue(employee)
                .retrieve()
                .bodyToMono(String.class)
                .map(responseString -> {
                    try {
                        return objectMapper.readValue(responseString, new TypeReference<>() {
                        });
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException();
                    }
        });
    }

    @Transactional
    public Mono<Employee> update(Employee employee) {
        String queryURL = "http://localhost:9999/dbh-command-api/dbh-employee-command";
        WebClient webClient = WebClient.builder().build();
        return webClient
                .put()
                .uri(queryURL)
                .bodyValue(employee)
                .retrieve()
                .bodyToMono(String.class)
                .map(responseString -> {
                    try {
                        return objectMapper.readValue(responseString, new TypeReference<>() {
                        });
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException();
                    }
        });
    }

    @Transactional(readOnly = true)
    public Mono<Optional<Employee>> findById(Long id) {
        return Mono.fromSupplier(() -> employeeRepository.findById(id));
    }

    @Transactional
    public Mono<Void> delete(Long id) throws JsonProcessingException {
        String queryURL = "http://localhost:9999/dbh-command-api/dbh-employee-command/{id}";
        WebClient webClient = WebClient.builder().build();
        return webClient
                .delete()
                .uri(queryURL, id)
                .retrieve()
                .bodyToMono(void.class);
    }

    @Transactional(readOnly = true)
    public Mono<Optional<Employee>> findByEmail(String email) {
        return Mono.fromSupplier(() -> employeeRepository.findByEmail(email));
    }

    @Transactional(readOnly = true)
    public Mono<Employee> findByEmployeeId(Long id) {
        String queryURL = "http://localhost:9999/dbh-query-api/dbh-employee-query/{id}";
        WebClient webClient = WebClient.builder().build();
        return webClient
                .get()
                .uri(queryURL, id)
                .retrieve()
                .bodyToMono(String.class)
                .map(responseString -> {
                    try {
                        return objectMapper.readValue(responseString, new TypeReference<>() {
                        });
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException();
                    }
        });
    }

    @Transactional(readOnly = true)
    public Flux<Employee> findAll() {
        String queryURL = "http://localhost:9999/dbh-query-api/dbh-employee-query";
        WebClient webClient = WebClient.builder().build();
        return webClient
                .get()
                .uri(queryURL)
                .retrieve()
                .bodyToMono(String.class)
                .flatMapMany(responseString -> {
                    try {
                        List<Employee> employees = objectMapper.readValue(responseString, new TypeReference<>() {});
                        return Flux.fromIterable(employees);
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException("Error deserializing response", ex);
                    }
                });
    }

}