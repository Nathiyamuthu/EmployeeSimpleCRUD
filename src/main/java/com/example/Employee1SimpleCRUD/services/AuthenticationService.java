package com.example.Employee1SimpleCRUD.services;

import com.example.Employee1SimpleCRUD.dto.LoginRequest;
import com.example.Employee1SimpleCRUD.dto.LoginResponse;
import com.example.Employee1SimpleCRUD.entity.User;
import com.example.Employee1SimpleCRUD.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public Mono<LoginResponse> authenticate(LoginRequest loginRequest) {

        return userRepository.findByUsername(loginRequest.getUsername())
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Invalid username or password")
                ))
                .flatMap(user -> {

                    if (!passwordEncoder.matches(
                            loginRequest.getPassword(),
                            user.getPassword())) {

                        return Mono.error(
                                new RuntimeException("Invalid username or password")
                        );
                    }

                    String token = jwtService.generateToken(user);

                    return Mono.just(new LoginResponse(token));
                });
    }
}