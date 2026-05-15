package com.example.mascotas.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.mascotas.dto.ApiResponse;
import com.example.mascotas.dto.TratamientoResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TratamientoClient {

    private final WebClient.Builder webClientBuilder;

    private final String BASE_URL = "http://localhost:8089/api/v1/tratamientos";

    public List<TratamientoResponse> listarPorMascota(Long mascotaId, String token) {

        ApiResponse<List<TratamientoResponse>> response =
                webClientBuilder.build()
                        .get()
                        .uri(BASE_URL + "/mascota/" + mascotaId)
                        .header("Authorization", token)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ApiResponse<List<TratamientoResponse>>>() {})
                        .block();

        return response != null ? response.getData() : List.of();
    }
}