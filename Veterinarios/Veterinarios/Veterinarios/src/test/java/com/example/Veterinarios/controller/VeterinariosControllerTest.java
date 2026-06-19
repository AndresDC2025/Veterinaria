package com.example.Veterinarios.controller;

import com.example.Veterinarios.dto.ApiResponse;
import com.example.Veterinarios.dto.VeterinarioDTO;
import com.example.Veterinarios.dto.VeterinarioResponse;
import com.example.Veterinarios.model.Veterinarios;
import com.example.Veterinarios.security.JwtUtil;
import com.example.Veterinarios.service.VeterinariosService;
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

@WebMvcTest(VeterinariosController.class)
@ActiveProfiles("test")
class VeterinariosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VeterinariosService servicio;

    @MockBean
    private JwtUtil jwtUtil;

    private Veterinarios vetEjemplo() {
        return Veterinarios.builder()
                .id(1L)
                .nombre("Dr. Soto")
                .especialidad("Cirugía")
                .horario("Lunes-Viernes 09:00-18:00")
                .email("soto@veterinaria.cl")
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaCrearVeterinario() throws Exception {
        VeterinarioDTO dto = new VeterinarioDTO();
        dto.setNombre("Dr. Soto");
        dto.setEspecialidad("Cirugía");
        dto.setHorario("Lunes-Viernes 09:00-18:00");
        dto.setEmail("soto@veterinaria.cl");

        when(servicio.guardar(any(VeterinarioDTO.class))).thenReturn(vetEjemplo());

        mockMvc.perform(post("/api/v1/veterinarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaListarVeterinarios() throws Exception {
        when(servicio.listar()).thenReturn(List.of(vetEjemplo()));

        mockMvc.perform(get("/api/v1/veterinarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaEliminarVeterinario() throws Exception {
        mockMvc.perform(delete("/api/v1/veterinarios/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
