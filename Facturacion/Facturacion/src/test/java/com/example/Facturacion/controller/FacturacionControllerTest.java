package com.example.Facturacion.controller;

import com.example.Facturacion.dto.FacturacionDTO;
import com.example.Facturacion.model.Facturacion;
import com.example.Facturacion.security.JwtUtil;
import com.example.Facturacion.service.FacturacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacturacionController.class)
@ActiveProfiles("test")
class FacturacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacturacionService service;

    @MockBean
    private JwtUtil jwtUtil;

    private Facturacion facturaEjemplo() {
        Facturacion f = new Facturacion();
        f.setId(1);
        f.setMonto("25000");
        f.setMetodoP("Tarjeta");
        return f;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaCrearFactura() throws Exception {
        FacturacionDTO dto = new FacturacionDTO();
        dto.setMonto("25000");
        dto.setMetodoP("Tarjeta");

        when(service.crear(any(FacturacionDTO.class), anyString())).thenReturn(facturaEjemplo());

        mockMvc.perform(post("/api/v1/facturacion")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaListarFacturas() throws Exception {
        when(service.listar()).thenReturn(List.of(facturaEjemplo()));

        mockMvc.perform(get("/api/v1/facturacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaObtenerFacturaPorId() throws Exception {
        when(service.obtener(1)).thenReturn(facturaEjemplo());

        mockMvc.perform(get("/api/v1/facturacion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.success").value(true));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaEliminarFactura() throws Exception {
        mockMvc.perform(delete("/api/v1/facturacion/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
