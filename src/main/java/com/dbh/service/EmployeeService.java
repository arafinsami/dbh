package com.dbh.service;

import com.dbh.dto.projection.EmployeeProjection;
import com.dbh.entity.Employee;
import com.dbh.exception.ResourceNotFoundException;
import com.dbh.mapper.EmployeeMapper;
import com.dbh.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EntityManager entityManager;

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
    @Transactional(readOnly = true)
    public List<EmployeeProjection> findAll() {
        return entityManager.createQuery("select " +
                        "new com.dbh.dto.projection.EmployeeProjection(e.id, e.email) from Employee e",
                EmployeeProjection.class).getResultList();
    }

    /*@Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }*/
}