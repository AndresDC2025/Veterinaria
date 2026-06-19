package com.example.Tratamientos.controller;

import com.example.Tratamientos.dto.TratamientosDTO;
import com.example.Tratamientos.dto.TratamientosResponse;
import com.example.Tratamientos.security.JwtUtil;
import com.example.Tratamientos.service.TratamientosService;
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

@WebMvcTest(TratamientosController.class)
@ActiveProfiles("test")
class TratamientosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TratamientosService service;

    @MockBean
    private JwtUtil jwtUtil;

    private TratamientosDTO dtoEjemplo() {
        TratamientosDTO dto = new TratamientosDTO();
        dto.setNombre("Amoxicilina 500mg");
        dto.setDosis("2 veces al dia");
        dto.setDuracion("7 dias");
        dto.setIdHistorial(1);
        dto.setInventarioId(1L);
        return dto;
    }

    private TratamientosResponse responseEjemplo() {
        return TratamientosResponse.builder()
                .id(1L)
                .nombre("Amoxicilina 500mg")
                .dosis("2 veces al dia")
                .duracion("7 dias")
                .idHistorial(1)
                .build();
    }

    @Test
    @WithMockUser(roles = "VETERINARIO")
    void deberiaCrearTratamiento() throws Exception {
        when(service.crear(any(TratamientosDTO.class), anyString())).thenReturn(responseEjemplo());

        mockMvc.perform(post("/api/v1/tratamientos")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEjemplo()))
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.nombre").value("Amoxicilina 500mg"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void usuarioSinPermisoNoDeberiaCrearTratamiento() throws Exception {
        mockMvc.perform(post("/api/v1/tratamientos")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEjemplo()))
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaListarTratamientos() throws Exception {
        when(service.listar(anyString())).thenReturn(List.of(responseEjemplo()));

        mockMvc.perform(get("/api/v1/tratamientos")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].nombre").value("Amoxicilina 500mg"));
    }

    @Test
    @WithMockUser(roles = "VETERINARIO")
    void deberiaObtenerTratamientoPorId() throws Exception {
        when(service.obtener(1L, anyString())).thenReturn(responseEjemplo());

        mockMvc.perform(get("/api/v1/tratamientos/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.nombre").value("Amoxicilina 500mg"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaActualizarTratamiento() throws Exception {
        when(service.actualizar(eq(1L), any(TratamientosDTO.class), anyString())).thenReturn(responseEjemplo());

        mockMvc.perform(put("/api/v1/tratamientos/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEjemplo()))
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaEliminarTratamiento() throws Exception {
        mockMvc.perform(delete("/api/v1/tratamientos/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "VETERINARIO")
    void veterinarioNoDeberiaPoderEliminarTratamiento() throws Exception {
        mockMvc.perform(delete("/api/v1/tratamientos/1")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}
