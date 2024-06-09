package com.dbh.dto.projection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeProjection {

    private Long id;

    private String email;

    public EmployeeProjection(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
