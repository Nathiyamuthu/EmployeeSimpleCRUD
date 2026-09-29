package com.example.Employee1SimpleCRUD.dto;

import com.example.Employee1SimpleCRUD.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDepartmentResponse {

    private EmployeeResponseDto employee;
    private Department department;
}