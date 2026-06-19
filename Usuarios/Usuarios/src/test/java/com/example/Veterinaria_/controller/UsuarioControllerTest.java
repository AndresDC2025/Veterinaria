package com.example.Veterinaria_.controller;

import com.example.Veterinaria_.dto.UsuarioResponse;
import com.example.Veterinaria_.dto.UsuariosDTO;
import com.example.Veterinaria_.security.JwtUtil;
import com.example.Veterinaria_.service.UsuarioService;
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

@WebMvcTest(UsuarioController.class)
@ActiveProfiles("test")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService service;

    @MockBean
    private JwtUtil jwtUtil;

    private UsuariosDTO usuarioDTOEjemplo() {
        return new UsuariosDTO("Juan Perez", "11111111-1", "juan@mail.com", "+56911111111", "Calle Falsa 123", 2);
    }

    private UsuarioResponse usuarioResponseEjemplo() {
        return UsuarioResponse.builder()
                .id(1L)
                .nombre("Juan Perez")
                .rut("11111111-1")
                .email("juan@mail.com")
                .telefono("+56911111111")
                .direccion("Calle Falsa 123")
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaCrearUsuario() throws Exception {
        when(service.crear(any(UsuariosDTO.class), anyString())).thenReturn(usuarioResponseEjemplo());

        mockMvc.perform(post("/api/usuarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTOEjemplo()))
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.nombre").value("Juan Perez"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void usuarioSinPermisoNoDeberiaCrear() throws Exception {
        mockMvc.perform(post("/api/usuarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTOEjemplo()))
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaListarUsuarios() throws Exception {
        when(service.listar(anyString())).thenReturn(List.of(usuarioResponseEjemplo()));

        mockMvc.perform(get("/api/usuarios")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].nombre").value("Juan Perez"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deberiaObtenerUsuarioPorId() throws Exception {
        when(service.obtener(1L, anyString())).thenReturn(usuarioResponseEjemplo());

        mockMvc.perform(get("/api/usuarios/1")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.nombre").value("Juan Perez"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaActualizarUsuario() throws Exception {
        when(service.actualizar(eq(1L), any(UsuariosDTO.class), anyString())).thenReturn(usuarioResponseEjemplo());

        mockMvc.perform(put("/api/usuarios/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTOEjemplo()))
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaEliminarUsuario() throws Exception {
        mockMvc.perform(delete("/api/usuarios/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
