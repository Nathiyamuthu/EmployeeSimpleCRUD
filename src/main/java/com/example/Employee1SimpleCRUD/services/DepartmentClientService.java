package com.example.Employee1SimpleCRUD.services;

import com.example.Employee1SimpleCRUD.entity.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DepartmentClientService {

    private final WebClientUtility  webClientUtility;

    public Mono<Department> getDepartment(Long deptNo){
        return  webClientUtility.get("http://localhost:8081/api/departments/"+deptNo,Department.class);
    }

    public Mono<Department> createDepartment(Department department){
        return webClientUtility.post("http://localhost:8081/api/departments",department,Department.class);

    }

    public Mono<Department> updateDepartment(Long deptNo, Department department) {
        return  webClientUtility.put("http://localhost:8081/api/departments/" +deptNo,department,Department.class);


    }

    public Mono<String> deleteDepartment(Long deptNo) {
        return webClientUtility.delete("http://localhost:8081/api/departments/" +deptNo,String.class);
    }
}
