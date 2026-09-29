package com.example.Employee1SimpleCRUD.controller;
import com.example.Employee1SimpleCRUD.dto.ApiResponseStructure;
import com.example.Employee1SimpleCRUD.dto.EmployeeDepartmentResponse;
import com.example.Employee1SimpleCRUD.dto.EmployeeRequestDto;
import com.example.Employee1SimpleCRUD.dto.EmployeeResponseDto;
import com.example.Employee1SimpleCRUD.entity.Department;
import com.example.Employee1SimpleCRUD.services.DepartmentClientService;
import com.example.Employee1SimpleCRUD.services.EmployeeServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import io.micrometer.tracing.Tracer;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

@Slf4j
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final Tracer tracer;
    private final EmployeeServices employeeServices;
    private final CacheManager cacheManager;

    private final ObservationRegistry observationRegistry;


    private final DepartmentClientService departmentClientService;

    @PostMapping
    public Mono<ResponseEntity<ApiResponseStructure<EmployeeResponseDto>>> createEmployee(@RequestBody EmployeeRequestDto employeeRequestDto){

        log.info("Received request to create employee");


        return  employeeServices.createEmployee(employeeRequestDto)
                .map(responseDto ->
                        ResponseEntity.status(HttpStatus.CREATED)
                        .body(
                                ApiResponseStructure.success("Employee created successfully", responseDto )
                        ));
    }




    @PutMapping("/{empId}")
    public Mono<ResponseEntity<ApiResponseStructure<EmployeeResponseDto>>> updateEmployee(@PathVariable Long empId,@RequestBody EmployeeRequestDto employeeRequestDto
    ){
        log.info("Received a request to update the exployee data");
        return employeeServices.updateEmployee(empId,employeeRequestDto)
                .map(responseDto ->ResponseEntity.status(HttpStatus.OK).body(
                        ApiResponseStructure.success("Employee update Successfully", responseDto)));
    }


    @GetMapping("/{empId}")
    public Mono<ResponseEntity<ApiResponseStructure<EmployeeResponseDto>>> fetchEmployee(
            @PathVariable Long empId) {

        Cache cache = cacheManager.getCache("employees");

        boolean fromCache = cache != null && cache.get(empId) != null;

        return employeeServices.fetchEmployee(empId)
                .map(response -> {

                    String message = fromCache
                            ? "Data fetched from Redis Cache"
                            : "Data fetched from Database";

                    return ResponseEntity.ok(
                            ApiResponseStructure.success(
                                    message,
                                    response
                            )
                    );
                });
    }

    @DeleteMapping("/{empId}")
    public Mono<ResponseEntity<ApiResponseStructure<String>>> deleteEmployee(@PathVariable Long empId){
        log.info("Received the request to delete the employee data");
        return employeeServices.deleteEmployee(empId)
                .thenReturn(
                        ResponseEntity.ok(
                                ApiResponseStructure.success(
                                        "Employee deleted successfully",
                                        "Employee ID: " + empId
                                )
                        )

                );


    }
    @PatchMapping("/{empId}")
    public Mono<ResponseEntity<ApiResponseStructure<EmployeeResponseDto>>> updateSalary(@PathVariable Long empId, @RequestBody EmployeeRequestDto employeeRequestDto)

    {
        log.info("Received the request to update the employee data partically");
        return employeeServices.updateSalary(empId,employeeRequestDto)
                .map(responseDto ->
                        ResponseEntity.ok(
                                ApiResponseStructure.success("Salary is updated successfully ", responseDto)
                        )
                );

    }



    /////////////WebClient For Department///////////////
    @GetMapping("/department/{deptNo}")
    public Mono<Department> getDepartment(@PathVariable Long deptNo){
        log.info("Received the request to fetch the Department data");

        return  departmentClientService.getDepartment(deptNo);
    }


    @PostMapping("/department")
    public  Mono<Department> createDepartment(@RequestBody Department department){
        log.info("received the request to create the Department");

        return departmentClientService.createDepartment(department);
    }

    @PutMapping("/department/{deptNo}")
    public Mono<Department> updateDepartment(@PathVariable Long deptNo, @RequestBody Department department){
        log.info("Received the request to update the Department data");
        return departmentClientService.updateDepartment(deptNo, department);
    }
    @DeleteMapping("/department/{deptNo}")
    public Mono<String> deleteDepartment(@PathVariable Long deptNo){
        log.info("Received request to delete the Department");
        return departmentClientService.deleteDepartment(deptNo);
    }


    /////////////////////////////Employee + Department
    @GetMapping("/with-department/{empId}")
    public Mono<EmployeeDepartmentResponse> fetchEmployeeWithDepartment(

            @PathVariable Long empId) {
        log.info("Received the request to fetch both Employee and Department Data");

        return employeeServices.fetchEmployeeWithDepartment(empId);
    }


}
