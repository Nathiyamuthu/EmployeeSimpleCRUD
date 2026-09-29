package com.example.Employee1SimpleCRUD.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {


    @JsonProperty("eName")
    private String eName;
    private int age;
    private double salary;
    private LocalDate joiningDate;
    private String dept;
    private Long deptNo;

}
