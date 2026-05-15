package com.example.Facturacion.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventarioClient {

    private final WebClient.Builder webClientBuilder;

    private final String INVENTARIO_URL =
            "http://localhost:8092/api/inventario/";

    public void descontarStock(
            Integer productoId,
            Integer cantidad,
            String token) {

        webClientBuilder.build()
                .put()
                .uri(INVENTARIO_URL +
                        "/descontar/" +
                        productoId +
                        "?cantidad=" +
                        cantidad)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}