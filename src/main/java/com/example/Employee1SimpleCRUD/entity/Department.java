package com.example.Employee1SimpleCRUD.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    private Long deptNo;
    private String deptName;
    private String manager;
}