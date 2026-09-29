//package com.example.Employee1SimpleCRUD.exception;
//
//
//import com.example.Employee1SimpleCRUD.dto.ApiResponseStructure;
//import com.example.Employee1SimpleCRUD.exception.IdNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(IdNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ApiResponseStructure<Void> handleIdNotFoundException(IdNotFoundException ex) {
//
//        return new ApiResponseStructure.failure(
//
//                ex.getMessage(),
//                null
//        );
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleRuntimeException(RuntimeException ex) {
//
//        return new ErrorResponse(
//                false,
//                ex.getMessage()
//        );
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorResponse handleException(Exception ex) {
//
//        return new ErrorResponse(
//                false,
//                "Something went wrong"
//        );
//    }
//}
//
//
package com.example.Employee1SimpleCRUD.exception;

import com.example.Employee1SimpleCRUD.dto.ApiResponseStructure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Employee ID / Department ID not found
    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponseStructure<Void> handleIdNotFoundException(
            IdNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());

        return ApiResponseStructure.failure(
                ex.getMessage(),
                null
        );
    }

    // Other Runtime Exceptions
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseStructure<Void> handleRuntimeException(
            RuntimeException ex) {

        return ApiResponseStructure.failure(
                ex.getMessage(),
                null
        );
    }

    // Any unexpected exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponseStructure<Void> handleException(
            Exception ex) {
        log.error("Unexpected server error", ex);

        return ApiResponseStructure.failure(
                "Something went wrong",
                null
        );
    }
}


