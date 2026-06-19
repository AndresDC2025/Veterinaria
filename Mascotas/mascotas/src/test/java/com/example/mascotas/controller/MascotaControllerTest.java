package com.example.mascotas.controller;

import com.example.mascotas.dto.MascotaDTO;
import com.example.mascotas.model.Mascota;
import com.example.mascotas.security.JwtUtil;
import com.example.mascotas.service.MascotaService;
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

@WebMvcTest(MascotaController.class)
@ActiveProfiles("test")
class MascotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MascotaService service;

    @MockBean
    private JwtUtil jwtUtil;

    private MascotaDTO dtoEjemplo() {
        MascotaDTO dto = new MascotaDTO();
        dto.setNombre("Max");
        dto.setRaza("Labrador");
        dto.setEdad(3);
        return dto;
    }

    private Mascota mascotaEjemplo() {
        Mascota m = new Mascota();
        m.setId(1);
        m.setNombre("Max");
        m.setRaza("Labrador");
        m.setEdad(3);
        return m;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaCrearMascota() throws Exception {
        when(service.crear(any(MascotaDTO.class))).thenReturn(mascotaEjemplo());

        mockMvc.perform(post("/api/v1/mascotas")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEjemplo())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.nombre").value("Max"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaListarMascotas() throws Exception {
        when(service.listar()).thenReturn(List.of(mascotaEjemplo()));

        mockMvc.perform(get("/api/v1/mascotas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].nombre").value("Max"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaObtenerMascotaPorId() throws Exception {
        when(service.obtener(eq(1L), anyString())).thenReturn(mascotaResponseEjemplo());

        mockMvc.perform(get("/api/v1/mascotas/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.nombre").value("Max"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaActualizarMascota() throws Exception {
        when(service.actualizar(eq(1L), any(MascotaDTO.class))).thenReturn(mascotaEjemplo());

        mockMvc.perform(put("/api/v1/mascotas/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEjemplo()))
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaEliminarMascota() throws Exception {
        mockMvc.perform(delete("/api/v1/mascotas/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    private com.example.mascotas.dto.MascotaResponse mascotaResponseEjemplo() {
        return com.example.mascotas.dto.MascotaResponse.builder()
                .id(1)
                .nombre("Max")
                .raza("Labrador")
                .edad(3)
                .build();
    }
}
