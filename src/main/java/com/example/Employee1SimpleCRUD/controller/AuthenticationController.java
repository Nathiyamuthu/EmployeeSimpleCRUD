package com.example.Employee1SimpleCRUD.controller;

import com.example.Employee1SimpleCRUD.dto.LoginRequest;
import com.example.Employee1SimpleCRUD.dto.LoginResponse;
import com.example.Employee1SimpleCRUD.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public Mono<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        return authenticationService.authenticate(loginRequest);
    }
}