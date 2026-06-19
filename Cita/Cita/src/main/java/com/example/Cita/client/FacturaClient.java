package com.example.Cita.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.Cita.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FacturaClient {

    private final WebClient.Builder webClientBuilder;

    private final String FACTURACION_URL = "http://localhost:8087/api/facturacion";

    public void generarFacturaAutomatica(Integer usuarioId, Integer citaId, String token) {

        Object facturacionRequest = new Object() {
            public final Double monto = 15000.0; 
            public final String metodoPago = "PENDIENTE";
        };

        webClientBuilder.build()
            .post()
            .uri(FACTURACION_URL + "/usuario/" + usuarioId + "/cita/" + citaId)
            .header("Authorization", token)
            .bodyValue(facturacionRequest)
            .retrieve()
            .bodyToMono(ApiResponse.class)
            .subscribe(); 
    }
}