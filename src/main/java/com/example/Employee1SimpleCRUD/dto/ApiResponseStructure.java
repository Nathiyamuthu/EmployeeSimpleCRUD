//package com.example.Employee1SimpleCRUD.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Data
//@Builder
//@NoArgsConstructor
//
//@AllArgsConstructor
//public class ApiResponseStructure<T> {
//
//    private boolean success;
//    private String message;
//    private T data;
//
//    @Builder.Default
//    private LocalDateTime time  = LocalDateTime.now();
//
//    public  static <T> ApiResponseStructure<T> success(String message , T Data){
//        return ApiResponseStructure.<T>builder()
//                .success(true)
//                .message(message)
//                .data(data)
//                .build();
//    }
//
//
//}

package com.example.Employee1SimpleCRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseStructure<T> {

    private boolean success;
    private String message;
    private T data;

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    public static <T> ApiResponseStructure<T> success(String message, T data) {
        return ApiResponseStructure.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponseStructure<T> failure(String message, T data) {
        return ApiResponseStructure.<T>builder()
                .success(false)
                .message(message)
                .data(data)
                .build();
    }
}
