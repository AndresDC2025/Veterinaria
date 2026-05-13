package com.example.Veterinaria_.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.Veterinaria_.dto.MascotaResponse;
import com.example.Veterinaria_.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;

@Component
@RequiredArgsConstructor
public class MascotaClient {

    private final WebClient webClient;
    // Asegúrate de que el microservicio de Mascotas esté en el 8085
    private final String BASE_URL = "http://localhost:8085/api/v1/mascotas/";

    public MascotaResponse obtenerMascota(Integer id, String token) {
        if (id == null) return null;
        try {
            // Se debe envolver en ApiResponse tal como en el ejemplo del AutorClient
            ApiResponse<MascotaResponse> response = webClient.get()
                    .uri(BASE_URL + id)
                    .header("Authorization", token) // El token debe ser válido
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<MascotaResponse>>() {})
                    .block();

            return (response != null) ? response.getData() : null;
        } catch (Exception e) {
            // Si entra aquí, puede ser por puerto cerrado o token inválido
            return null;
        }
    }
}