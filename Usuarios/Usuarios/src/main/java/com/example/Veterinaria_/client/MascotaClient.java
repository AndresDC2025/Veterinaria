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
    private final String BASE_URL = "http://localhost:8085/api/mascotas/";

    public MascotaResponse obtenerMascota(Integer id, String token) {
        try {
            // Petición GET reactiva bloqueante para integrarse con el flujo imperativo del Service
            ApiResponse<MascotaResponse> response = webClient.get()
                    .uri(BASE_URL + id)
                    .header("Authorization", token) // Se añade el token para validar la sesión
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<MascotaResponse>>() {})
                    .block();

            return (response != null) ? response.getData() : null;
        } catch (Exception e) {
            // Si el microservicio de mascotas falla o el ID no existe
            return null;
        }
    }
}