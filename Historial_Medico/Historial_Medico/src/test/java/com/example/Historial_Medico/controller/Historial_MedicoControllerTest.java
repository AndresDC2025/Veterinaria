package com.example.Historial_Medico.controller;

import com.example.Historial_Medico.dto.Historial_MedicoDTO;
import com.example.Historial_Medico.dto.Historial_MedicoResponse;
import com.example.Historial_Medico.security.JwtUtil;
import com.example.Historial_Medico.service.Historial_MedicoService;
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

@WebMvcTest(Historial_MedicoController.class)
@ActiveProfiles("test")
class Historial_MedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private Historial_MedicoService service;

    @MockBean
    private JwtUtil jwtUtil;

    private Historial_MedicoDTO dtoEjemplo() {
        Historial_MedicoDTO dto = new Historial_MedicoDTO();
        dto.setDiagnostico("Gripe canina");
        dto.setTratamiento("Reposo y antibioticos");
        dto.setDescripcion("Mascota con tos y fiebre");
        dto.setIdMascota(1L);
        return dto;
    }

    private Historial_MedicoResponse responseEjemplo() {
        return Historial_MedicoResponse.builder()
                .id(1)
                .diagnostico("Gripe canina")
                .tratamiento("Reposo y antibioticos")
                .descripcion("Mascota con tos y fiebre")
                .idmascota(1)
                .build();
    }

    @Test
    @WithMockUser(roles = "VETERINARIO")
    void deberiaCrearHistorial() throws Exception {
        when(service.crear(any(Historial_MedicoDTO.class), anyString())).thenReturn(responseEjemplo());

        mockMvc.perform(post("/api/v1/historiales")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEjemplo()))
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.diagnostico").value("Gripe canina"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void usuarioSinPermisoNoDeberiaCrearHistorial() throws Exception {
        mockMvc.perform(post("/api/v1/historiales")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEjemplo()))
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaListarHistoriales() throws Exception {
        when(service.listar(anyString())).thenReturn(List.of(responseEjemplo()));

        mockMvc.perform(get("/api/v1/historiales")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].diagnostico").value("Gripe canina"));
    }

    @Test
    @WithMockUser(roles = "VETERINARIO")
    void deberiaObtenerHistorialPorId() throws Exception {
        when(service.obtener(1L, anyString())).thenReturn(responseEjemplo());

        mockMvc.perform(get("/api/v1/historiales/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.diagnostico").value("Gripe canina"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaActualizarHistorial() throws Exception {
        when(service.actualizar(eq(1L), any(Historial_MedicoDTO.class), anyString())).thenReturn(responseEjemplo());

        mockMvc.perform(put("/api/v1/historiales/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEjemplo()))
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaEliminarHistorial() throws Exception {
        mockMvc.perform(delete("/api/v1/historiales/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "VETERINARIO")
    void veterinarioNoDeberiaPoderEliminarHistorial() throws Exception {
        mockMvc.perform(delete("/api/v1/historiales/1")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}
