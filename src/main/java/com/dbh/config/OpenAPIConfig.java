package com.dbh.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "MD. SAMIUL ARAFIN",
                        email = "dbh@dbh.com",
                        url = "https://github.com/dbh"
                ),
                description = "OpenApi documentation for DBH",
                title = "OpenApi specification - dbh",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local environment",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenAPIConfig {
}
