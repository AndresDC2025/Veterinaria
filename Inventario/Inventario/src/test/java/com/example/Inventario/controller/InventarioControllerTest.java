package com.example.Inventario.controller;

import com.example.Inventario.dto.InventarioDTO;
import com.example.Inventario.model.Inventario;
import com.example.Inventario.security.JwtUtil;
import com.example.Inventario.service.InventarioService;
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

@WebMvcTest(InventarioController.class)
@ActiveProfiles("test")
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InventarioService servicio;

    @MockBean
    private JwtUtil jwtUtil;

    private Inventario insumoEjemplo() {
        return Inventario.builder().id(1).nombre("Amoxicilina").stock(100).proveedor("Farma Chile").build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaCrearInsumo() throws Exception {
        InventarioDTO dto = new InventarioDTO();
        dto.setNombre("Amoxicilina");
        dto.setStock(100);
        dto.setProveedor("Farma Chile");

        when(servicio.guardar(any(InventarioDTO.class))).thenReturn(insumoEjemplo());

        mockMvc.perform(post("/api/inventario")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaListarInventario() throws Exception {
        when(servicio.listar()).thenReturn(List.of(insumoEjemplo()));

        mockMvc.perform(get("/api/inventario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaObtenerInsumoPorId() throws Exception {
        when(servicio.obtener(1)).thenReturn(insumoEjemplo());

        mockMvc.perform(get("/api/inventario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.success").value(true));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaEliminarInsumo() throws Exception {
        mockMvc.perform(delete("/api/inventario/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
