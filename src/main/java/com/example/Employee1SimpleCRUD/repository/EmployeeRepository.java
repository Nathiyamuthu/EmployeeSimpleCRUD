package com.example.Employee1SimpleCRUD.repository;

import com.example.Employee1SimpleCRUD.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee,Long> {

}
