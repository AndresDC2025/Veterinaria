package com.example.Resena.controller;

import com.example.Resena.dto.ResenaDTO;
import com.example.Resena.model.Resena;
import com.example.Resena.security.JwtUtil;
import com.example.Resena.service.ResenaService;
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

@WebMvcTest(ResenaController.class)
@ActiveProfiles("test")
class ResenaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ResenaService service;

    @MockBean
    private JwtUtil jwtUtil;

    private Resena resenaEjemplo() {
        return Resena.builder()
                .id(1L)
                .opinion("Excelente atención")
                .estrellas(5)
                .veterinarioId(1L)
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaCrearResena() throws Exception {
        ResenaDTO dto = new ResenaDTO();
        dto.setOpinion("Excelente atención");
        dto.setEstrellas(5);
        dto.setVeterinarioId(1L);

        when(service.save(any(ResenaDTO.class))).thenReturn(resenaEjemplo());

        mockMvc.perform(post("/api/v1/resena")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaListarResenas() throws Exception {
        when(service.listar()).thenReturn(List.of(resenaEjemplo()));

        mockMvc.perform(get("/api/v1/resena"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaObtenerResenaPorId() throws Exception {
        when(service.getById(1L)).thenReturn(resenaEjemplo());

        mockMvc.perform(get("/api/v1/resena/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.success").value(true));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaEliminarResena() throws Exception {
        mockMvc.perform(delete("/api/v1/resena/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
