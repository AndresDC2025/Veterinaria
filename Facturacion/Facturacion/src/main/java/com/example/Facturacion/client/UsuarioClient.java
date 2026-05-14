package com.example.Facturacion.client;

import com.example.Facturacion.dto.ApiResponse;
import com.example.Facturacion.dto.UsuarioResponse; // Debes crear este DTO en Facturación
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class UsuarioClient {

    private final WebClient webClient;
    private final String BASE_URL = "http://localhost:8086/api/v1/usuarios/";

    public UsuarioResponse obtenerUsuario(Integer id, String token) {
        if (id == null) return null;
        try {
            ApiResponse<UsuarioResponse> response = webClient.get()
                    .uri(BASE_URL + id)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<UsuarioResponse>>() {})
                    .block();

            return (response != null) ? response.getData() : null;
        } catch (Exception e) {
            return null;
        }
    }
}