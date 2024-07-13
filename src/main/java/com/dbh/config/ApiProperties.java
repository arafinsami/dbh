package com.dbh.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApiProperties {

    @Value("${command.api.host}")
    private String commandApiHost;

    @Value("${command.api.path}")
    private String commandApiPath;

    @Value("${query.api.host}")
    private String queryApiHost;

    @Value("${query.api.path}")
    private String queryApiPath;
}
