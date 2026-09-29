//package com.example.Employee1SimpleCRUD.services;
//
//import com.example.Employee1SimpleCRUD.dto.ApiResponseStructure;
//import com.example.Employee1SimpleCRUD.dto.EmployeeRequestDto;
//import com.example.Employee1SimpleCRUD.dto.EmployeeResponseDto;
//import com.example.Employee1SimpleCRUD.entity.Employee;
//import com.example.Employee1SimpleCRUD.repository.EmployeeRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//@Service
//@RequiredArgsConstructor
//public class EmployeeServices {
//    private final EmployeeRepository employeeRepository;
//
//    public Mono<EmployeeResponseDto> createEmployee(EmployeeRequestDto employeeRequestDto) {
//
//        Employee employee = Employee.builder()
//                .eName(employeeRequestDto.getEName())
//                .age(employeeRequestDto.getAge())
//                .salary(employeeRequestDto.getSalary())
//                .joiningDate(employeeRequestDto.getJoiningDate())
//                .dept(employeeRequestDto.getDept())
//                .build();
//
//        return employeeRepository.save(employee)
//                .map(saveEmployee -> EmployeeResponseDto.builder()
//                        .empId(saveEmployee.getEmpId())
//                        .eName(saveEmployee.getEName())
//                        .age(saveEmployee.getAge())
//                        .salary(saveEmployee.getSalary())
//                        .joiningData(saveEmployee.getJoiningDate())
//                        .dept(saveEmployee.getDept())
//                        .build()
//                );
//
//
//    }
//
//
//
//}
package com.example.Employee1SimpleCRUD.services;

import com.example.Employee1SimpleCRUD.dto.EmployeeDepartmentResponse;
import com.example.Employee1SimpleCRUD.dto.EmployeeRequestDto;
import com.example.Employee1SimpleCRUD.dto.EmployeeResponseDto;
import com.example.Employee1SimpleCRUD.entity.Employee;
import com.example.Employee1SimpleCRUD.exception.IdNotFoundException;
import com.example.Employee1SimpleCRUD.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.cache.annotation.CachePut;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServices {

    private final EmployeeRepository employeeRepository;
    private final DepartmentClientService departmentClientService;

    public Mono<EmployeeResponseDto> createEmployee(
            EmployeeRequestDto employeeRequestDto) {
        log.info("Creating employee with name: {}",
                employeeRequestDto.getEName());

        Employee employee = Employee.builder()
                .eName(employeeRequestDto.getEName())
                .age(employeeRequestDto.getAge())
                .salary(employeeRequestDto.getSalary())
                .joiningDate(employeeRequestDto.getJoiningDate())
                .dept(employeeRequestDto.getDept())
                .deptNo(employeeRequestDto.getDeptNo())
                .build();
        log.info("Employee name before save: {}" , employee.getEName());

        return employeeRepository.save(employee)
                .map(saveEmployee -> {
                    log.info("Employee created successfully with id: {}",
                            saveEmployee.getEmpId());

                    return EmployeeResponseDto.builder()
                            .empId(saveEmployee.getEmpId())
                            .eName(saveEmployee.getEName())
                            .age(saveEmployee.getAge())
                            .salary(saveEmployee.getSalary())
                            .joiningDate(saveEmployee.getJoiningDate())
                            .dept(saveEmployee.getDept())
                            .build();
                });
    }

    ////////////////////////////////////////
     @CachePut(value = "employees", key = "#empId")
    public Mono<EmployeeResponseDto> updateEmployee(Long empId,EmployeeRequestDto employeeRequestDto){
        log.info("Updating employee with id: {}", empId);
        return employeeRepository.findById(empId)
                .switchIfEmpty(Mono.error(new IdNotFoundException("Employee Doesn't exist with id: "+empId)
                        )
                )
                        .flatMap(employee->{
                            log.info("Employee found with id: {}. Updating details",
                                    empId);
                          employee.setEName(employeeRequestDto.getEName());
                          employee.setAge(employeeRequestDto.getAge());
                          employee.setSalary(employeeRequestDto.getSalary());
                          employee.setJoiningDate(employeeRequestDto.getJoiningDate());
                          employee.setDept(employeeRequestDto.getDept());

                          return employeeRepository.save(employee);
                        })
                .map(saveEmployee -> {
                    log.info("Employee updated successfully with id: {}",
                            saveEmployee.getEmpId());
                    return EmployeeResponseDto.builder()
                            .empId(saveEmployee.getEmpId())
                            .eName(saveEmployee.getEName())
                            .age(saveEmployee.getAge())
                            .salary(saveEmployee.getSalary())
                            .joiningDate(saveEmployee.getJoiningDate())
                            .dept(saveEmployee.getDept())
                            .build();
                });

    }
    @Cacheable(
            key = "#empId",
            value = "employees",
            unless = "#result.salary < 50000"
    )
    public Mono<EmployeeResponseDto> fetchEmployee(Long empId) {

        log.info("Data not found in Redis cache. Fetching employee with id: {} from DATABASE", empId);

        return employeeRepository.findById(empId)
                .switchIfEmpty(
                        Mono.error(
                                new IdNotFoundException(
                                        "Employee Doesn't exist with id: " + empId
                                )
                        )
                )
                .map(fetchedData -> {

                    log.info(
                            "Employee with id: {} fetched successfully from DATABASE",
                            empId
                    );

                    return EmployeeResponseDto.builder()
                            .empId(fetchedData.getEmpId())
                            .eName(fetchedData.getEName())
                            .age(fetchedData.getAge())
                            .salary(fetchedData.getSalary())
                            .joiningDate(fetchedData.getJoiningDate())
                            .dept(fetchedData.getDept())
                            .deptNo(fetchedData.getDeptNo())
                            .build();
                });
    }
    ////////////////////////////////////////////
    @CacheEvict(value = "employees", key = "#empId")
    public Mono<Void> deleteEmployee(Long empId){
        log.info("Deleting employee with id: {}", empId);
        return employeeRepository.findById(empId)
                .switchIfEmpty(Mono.error(new IdNotFoundException("Employee Doesn't exist with id: "+empId)
                ))
                .flatMap(deletedEmployee ->
                        employeeRepository.deleteById(empId));
    }

    //////////////////////////////////////////
    @CachePut(value = "employees", key = "#empId")
    public Mono<EmployeeResponseDto> updateSalary(Long empId,EmployeeRequestDto employeeRequestDto){


        return employeeRepository.findById(empId)
                .switchIfEmpty(
                        Mono.error(
                                new IdNotFoundException("Employee does not exist with that id "+empId)
                        )
                )
                .flatMap(employee ->{

                    employee.setSalary(employeeRequestDto.getSalary());

                    return employeeRepository.save(employee);
                })
                .map(updatedEmployee -> EmployeeResponseDto.builder()
                        .eName(updatedEmployee.getEName())
                        .age(updatedEmployee.getAge())
                        .salary(updatedEmployee.getSalary())
                        .joiningDate(updatedEmployee.getJoiningDate())
                        .dept(updatedEmployee.getDept())
                        .build()
                );
    }


    public Mono<EmployeeDepartmentResponse> fetchEmployeeWithDepartment(Long empId) {

        return employeeRepository.findById(empId)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Employee Doesn't exist")
                ))

                .flatMap(employee ->
                        departmentClientService.getDepartment(employee.getDeptNo())
                                .map(department -> new EmployeeDepartmentResponse(
                                        EmployeeResponseDto.builder()
                                                .empId(employee.getEmpId())
                                                .eName(employee.getEName())
                                                .age(employee.getAge())
                                                .salary(employee.getSalary())
                                                .joiningDate(employee.getJoiningDate())
                                                .dept(employee.getDept())
                                                .deptNo(employee.getDeptNo())
                                                .build(),

                                        department
                                ))
                );
    }

}
