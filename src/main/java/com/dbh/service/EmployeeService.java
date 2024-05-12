package com.dbh.service;

import com.dbh.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll(int offset, int recordPerPage);

    void save(Employee employee);

    void update(Employee employee);

    void delete(int id);

    Employee findById(int id);

    Integer count();
}
