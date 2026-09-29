package com.example.Employee1SimpleCRUD.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebClientUtility {

    private final WebClient webClient;

    public <R> Mono<R> get(String url, Class<R> responseType){
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T, R> Mono<R> post(String url, T requestBody, Class<R> responseType){
        return  webClient
                .post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()

                .bodyToMono(responseType);
    }

    public <T, R> Mono<R> put(
            String url,
            T requestBody,
            Class<R> responseType) {

        return webClient
                .put()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType);
    }

    public <R> Mono<R> delete(
            String url,
            Class<R> responseType) {

        return webClient
                .delete()
                .uri(url)
                .retrieve()
                .bodyToMono(responseType);
    }
}
