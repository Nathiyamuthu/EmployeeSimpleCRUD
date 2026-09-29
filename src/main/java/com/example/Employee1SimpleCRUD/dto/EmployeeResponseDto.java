//package com.example.Employee1SimpleCRUD.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class EmployeeResponseDto {
//
//    private long empId;
//    private  String eName;
//    private int age;
//    private double salary;
//    private LocalDate joiningData;
//    private String dept;
//
//}
package com.example.Employee1SimpleCRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto {

    private long empId;
    private String eName;
    private int age;
    private double salary;
    private LocalDate joiningDate;
    private String dept;
    private Long deptNo;
}