package com.example.Employee1SimpleCRUD.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("employee")
public class Employee {

    @Id
    @Column("emp_id")
    private Long empId;

    @Column("e_name")
    private String eName;

    @Column("age")
    private int age;

    @Column("salary")
    private double salary;

    @Column("joining_date")
    private LocalDate joiningDate;

    @Column("dept")
    private String dept;

    @Column("dept_no")
    private Long deptNo;
}