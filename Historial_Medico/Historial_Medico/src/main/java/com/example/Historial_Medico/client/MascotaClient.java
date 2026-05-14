package com.example.Historial_Medico.client;

import com.example.Historial_Medico.dto.ApiResponse;
import com.example.Historial_Medico.dto.MascotaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class MascotaClient {
    private final WebClient webClient;
    private final String BASE_URL = "http://localhost:8085/api/v1/mascotas/";

    public MascotaResponse obtenerMascota(Object id, String token) {
        if (id == null) return null;
        try {
            ApiResponse<MascotaResponse> response = webClient.get()
                    .uri(BASE_URL + id)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<MascotaResponse>>() {})
                    .block();
            return (response != null) ? response.getData() : null;
        } catch (Exception e) {
            return null;
        }
    }
}