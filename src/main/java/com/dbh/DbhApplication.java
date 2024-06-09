package com.dbh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DbhApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbhApplication.class, args);
	}

}
