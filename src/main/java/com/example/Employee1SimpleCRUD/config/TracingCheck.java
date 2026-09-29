package com.example.Employee1SimpleCRUD.config;

import io.micrometer.tracing.Tracer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TracingCheck {

    private final Tracer tracer;

    @PostConstruct
    public void checkTracing() {
        System.out.println("================================");
        System.out.println("Tracer = " + tracer);
        System.out.println("Current Span = " + tracer.currentSpan());
        System.out.println("================================");
    }
}