package com.example.Facturacion.client;

import com.example.Facturacion.dto.ApiResponse;
import com.example.Facturacion.dto.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class UsuarioClient {

    private final WebClient webClient;

    private static final String BASE_URL = "http://localhost:8086/api/usuarios";

    public UsuarioResponse obtenerUsuario(Integer id, String token) {

        if (id == null) {
            throw new IllegalArgumentException("ID de usuario es null");
        }

        try {
            ApiResponse<UsuarioResponse> response = webClient.get()
                    .uri(BASE_URL + "/" + id)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<UsuarioResponse>>() {})
                    .block();

            if (response == null || response.getData() == null) {
                throw new RuntimeException("Usuario no encontrado en usuario-service");
            }

            return response.getData();

        } catch (Exception e) {
            throw new RuntimeException("Error llamando usuario-service: " + e.getMessage(), e);
        }
    }
}