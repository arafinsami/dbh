package com.dbh.repository;

import com.dbh.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    /*@Query("SELECT e FROM Employee e")
    List<Employee> findAll();*/

    @Query(value = "SELECT * FROM employee", nativeQuery = true)
    List<Employee> findAll();
}
