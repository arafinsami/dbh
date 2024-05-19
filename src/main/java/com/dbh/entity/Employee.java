package com.dbh.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EMPLOYEE")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private Long id;

    @Column(name = "EMPLOYEE_NAME")
    private String name;

    @Column(name = "EMPLOYEE_EMAIL")
    private String email;

    @Column(name = "EMPLOYEE_PASSWORD")
    private String password;
}
