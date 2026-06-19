package com.example.Facturacion.client;


import com.example.Facturacion.dto.ApiResponse;
import com.example.Facturacion.dto.CitaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CitaClient {

    private final WebClient webClient;

    private final String BASE_URL = "http://localhost:8095/api/v1/citas/";

    public CitaResponse obtenerCita(Integer id, String token) {
        if (id == null) return null;

        try {
            ApiResponse<CitaResponse> response = webClient.get()
                    .uri(BASE_URL + id)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<CitaResponse>>() {})
                    .block();

            return (response != null) ? response.getData() : null;

        } catch (Exception e) {
            return null;
        }
    }
}