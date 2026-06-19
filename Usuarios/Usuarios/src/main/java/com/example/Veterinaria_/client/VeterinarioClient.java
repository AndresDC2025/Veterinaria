package com.example.Veterinaria_.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.Veterinaria_.dto.VeterinariosResponse;
import com.example.Veterinaria_.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;

@Component
@RequiredArgsConstructor
public class VeterinarioClient {

    private final WebClient webClient;
    private final String BASE_URL = "http://localhost:8084/api/v1/veterinarios/";

    public VeterinariosResponse obtenerVeterianrio(Integer id, String token) {
        if (id == null) return null;
        try {
            ApiResponse<VeterinariosResponse> response = webClient.get()
                    .uri(BASE_URL + id)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<VeterinariosResponse>>() {})
                    .block();

            return (response != null) ? response.getData() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
