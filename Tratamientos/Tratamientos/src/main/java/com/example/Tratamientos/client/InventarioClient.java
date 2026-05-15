package com.example.Tratamientos.client;

import com.example.Tratamientos.dto.ApiResponse;
import com.example.Tratamientos.dto.InventarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class InventarioClient {

    private final WebClient webClient;

    private final String BASE_URL = "http://localhost:8092/api/inventario/";

    public InventarioResponse obtenerInventario(Long id, String token) {

        if (id == null) return null;

        try {
            ApiResponse<InventarioResponse> response =
                    webClient.get()
                            .uri(BASE_URL + id)
                            .header("Authorization", token)
                            .retrieve()
                            .bodyToMono(new ParameterizedTypeReference<ApiResponse<InventarioResponse>>() {})
                            .block();

            return (response != null) ? response.getData() : null;

        } catch (Exception e) {
            return null;
        }
    }
}