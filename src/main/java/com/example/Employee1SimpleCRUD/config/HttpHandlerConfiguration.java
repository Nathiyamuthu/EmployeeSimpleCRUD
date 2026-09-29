package com.example.Employee1SimpleCRUD.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

@Configuration
public class HttpHandlerConfiguration {

    private final ApplicationContext applicationContext;

    public HttpHandlerConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public HttpHandler httpHandler(ObservationRegistry observationRegistry) {

        return WebHttpHandlerBuilder
                .applicationContext(this.applicationContext)
                .observationRegistry(observationRegistry)
                .build();
    }
}