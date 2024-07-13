package com.dbh.service;

import com.dbh.config.ApiProperties;
import com.dbh.dto.request.EmployeeRequest;
import com.dbh.dto.request.LoginRequestDTO;
import com.dbh.dto.request.SingUpRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeService {

    private final ApiProperties apiProperties;

    private final ObjectMapper objectMapper;

    public EmployeeRequest save(EmployeeRequest employee) throws JsonProcessingException {
        String queryURL = apiProperties.getCommandApiHost() + apiProperties.getCommandApiPath();
        WebClient webClient = WebClient.builder().build();
        Mono<String> response = webClient.post()
                .uri(queryURL)
                .bodyValue(employee)
                .retrieve()
                .bodyToMono(String.class);
        String responseString = response.block();
        return objectMapper.readValue(responseString, new TypeReference<>() {
        });
    }

    public EmployeeRequest update(EmployeeRequest employee, String token) throws JsonProcessingException {
        String queryURL = apiProperties.getCommandApiHost() + apiProperties.getCommandApiPath();
        WebClient webClient = WebClient.builder().build();
        Mono<String> response = webClient.put()
                .uri(queryURL)
                .headers(headers -> headers.setBearerAuth(token))
                .bodyValue(employee)
                .retrieve()
                .bodyToMono(String.class);
        String responseString = response.block();
        return objectMapper.readValue(responseString, new TypeReference<>() {
        });
    }

    public void delete(Long id) throws JsonProcessingException {
        String queryURL = apiProperties.getCommandApiHost() + apiProperties.getCommandApiPath() + "/{id}";
        WebClient webClient = WebClient.builder().build();
        Mono<String> response = webClient.delete()
                .uri(queryURL, id)
                .retrieve()
                .bodyToMono(String.class);
        String responseString = response.block();
        objectMapper.readValue(responseString, new TypeReference<>() {
        });
    }

    public EmployeeRequest findByEmployeeId(Long id, String token) throws JsonProcessingException {
        String queryURL = apiProperties.getQueryApiHost() + apiProperties.getQueryApiPath() + "/{id}";
        WebClient webClient = WebClient.builder().build();
        Mono<String> response = webClient.get()
                .uri(queryURL, id)
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .bodyToMono(String.class);
        String responseString = response.block();
        return objectMapper.readValue(responseString, new TypeReference<>() {});
    }

    public List<EmployeeRequest> findAll(String token) throws JsonProcessingException {
        String queryURL = apiProperties.getQueryApiHost() + apiProperties.getQueryApiPath();
        WebClient webClient = WebClient.builder().build();
        Mono<String> response = webClient.get()
                .uri(queryURL)
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .bodyToMono(String.class);
        String responseString = response.block();
        return objectMapper.readValue(responseString, new TypeReference<>() {
        });
    }

    public Object login(LoginRequestDTO login) throws JsonProcessingException {
        String queryURL = apiProperties.getQueryApiHost() + "authentication/login";
        WebClient webClient = WebClient.builder().build();
        Mono<String> response = webClient.post()
                .uri(queryURL)
                .bodyValue(login)
                .retrieve()
                .bodyToMono(String.class);
        String responseString = response.block();
        return objectMapper.readValue(responseString, new TypeReference<>() {
        });
    }

    public Object signup(SingUpRequestDTO singUp) throws JsonProcessingException {
        String queryURL = apiProperties.getQueryApiHost() + "authentication/signup";
        WebClient webClient = WebClient.builder().build();
        Mono<String> response = webClient.post()
                .uri(queryURL)
                .bodyValue(singUp)
                .retrieve()
                .bodyToMono(String.class);
        String responseString = response.block();
        return objectMapper.readValue(responseString, new TypeReference<>() {
        });
    }
}