package com.example.Veterinarios.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Veterinarios.dto.ApiResponse;
import com.example.Veterinarios.dto.ResenaResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResenaClient {

    private final WebClient.Builder webClientBuilder;

    private final String BASE_URL = "http://localhost:8094/api/v1/resena";

    public List<ResenaResponse> listarPorVeterinario(Long veterinarioId, String token) {

        return webClientBuilder.build()
                .get()
                .uri(BASE_URL + "/veterinario/" + veterinarioId)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<List<ResenaResponse>>>() {})
                .block()
                .getData();
    }
}