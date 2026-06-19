package com.example.Cita.controller;

import com.example.Cita.dto.CitaDTO;
import com.example.Cita.model.Cita;
import com.example.Cita.security.JwtUtil;
import com.example.Cita.service.CitaService;
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

@WebMvcTest(CitaController.class)
@ActiveProfiles("test")
class CitaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CitaService service;

    @MockBean
    private JwtUtil jwtUtil;

    private Cita citaEjemplo() {
        Cita c = new Cita();
        c.setId(1);
        c.setFecha("2026-06-18");
        c.setHora("10:00");
        c.setMotivo("Vacunación");
        c.setUsuarioId(1);
        c.setMascotaId(1);
        return c;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaCrearCita() throws Exception {
        CitaDTO dto = new CitaDTO();
        dto.setFecha("2026-06-18");
        dto.setHora("10:00");
        dto.setMotivo("Vacunación");
        dto.setUsuarioId(1);
        dto.setMascotaId(1);

        when(service.crear(any(CitaDTO.class), anyString())).thenReturn(citaEjemplo());

        mockMvc.perform(post("/api/v1/citas")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaListarCitas() throws Exception {
        when(service.listar()).thenReturn(List.of(citaEjemplo()));

        mockMvc.perform(get("/api/v1/citas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaObtenerCitaPorId() throws Exception {
        when(service.obtener(1L)).thenReturn(citaEjemplo());

        mockMvc.perform(get("/api/v1/citas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.success").value(true));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaEliminarCita() throws Exception {
        mockMvc.perform(delete("/api/v1/citas/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
