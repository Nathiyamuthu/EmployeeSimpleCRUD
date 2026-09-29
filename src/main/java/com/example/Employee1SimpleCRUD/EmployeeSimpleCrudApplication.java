package com.example.Employee1SimpleCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class EmployeeSimpleCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeSimpleCrudApplication.class, args);
	}

}
