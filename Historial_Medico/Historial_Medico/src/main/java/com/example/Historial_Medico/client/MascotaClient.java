package com.example.Historial_Medico.client;

import com.example.Historial_Medico.dto.ApiResponse;
import com.example.Historial_Medico.dto.MascotaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class MascotaClient {

    private final WebClient webClient;

    private final String BASE_URL = "http://localhost:8085/api/v1/mascotas/";

    public MascotaResponse obtenerMascota(Long id, String token) {

        log.info("Consultando mascota ID: {}", id);

        try {
            ApiResponse<MascotaResponse> response = webClient.get()
                    .uri(BASE_URL + id)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<MascotaResponse>>() {})
                    .block();

            log.info("RESPUESTA MASCOTA CLIENT: {}", response);

            if (response == null) return null;

            return response.getData();

        } catch (Exception e) {
            log.error("Error llamando Mascotas: {}", e.getMessage());
            return null;
        }
    }
}