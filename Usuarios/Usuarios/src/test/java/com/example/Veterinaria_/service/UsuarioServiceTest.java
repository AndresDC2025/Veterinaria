package com.example.Veterinaria_.service;

import com.example.Veterinaria_.client.MascotaClient;
import com.example.Veterinaria_.dto.MascotaResponse;
import com.example.Veterinaria_.dto.UsuarioResponse;
import com.example.Veterinaria_.dto.UsuariosDTO;
import com.example.Veterinaria_.model.Usuario;
import com.example.Veterinaria_.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock private UsuarioRepository repo;
    @Mock private MascotaClient mascotaClient;

    @InjectMocks
    private UsuarioService service;

    private UsuariosDTO usuarioDTOEjemplo() {
        return new UsuariosDTO("Juan Perez", "11111111-1", "juan@mail.com", "+56911111111", "Calle Falsa 123", 2);
    }

    private Usuario usuarioEjemplo() {
        return new Usuario(1L, "Juan Perez", "11111111-1", "juan@mail.com", "+56911111111", "Calle Falsa 123", 2);
    }

    private MascotaResponse mascotaEjemplo() {
        MascotaResponse mascota = new MascotaResponse();
        mascota.setId(2);
        mascota.setNombre("Firulais");
        mascota.setEspecie("Perro");
        mascota.setRaza("Labrador");
        mascota.setEdad(3);
        return mascota;
    }

    @Test
    void deberiaCrearUsuarioCuandoMascotaExiste() {
        // Given
        UsuariosDTO dto = usuarioDTOEjemplo();
        when(mascotaClient.obtenerMascota(eq(2), anyString())).thenReturn(mascotaEjemplo());
        when(repo.save(any(Usuario.class))).thenReturn(usuarioEjemplo());

        // When
        UsuarioResponse resultado = service.crear(dto, "Bearer token");

        // Then
        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());
        assertEquals("Firulais", resultado.getMascota().getNombre());
        verify(repo).save(any(Usuario.class));
    }

    @Test
    void noDeberiaCrearUsuarioCuandoMascotaNoExiste() {
        // Given
        UsuariosDTO dto = usuarioDTOEjemplo();
        when(mascotaClient.obtenerMascota(eq(2), anyString())).thenReturn(null);

        // When / Then
        assertThrows(RuntimeException.class, () -> service.crear(dto, "Bearer token"));
        verify(repo, never()).save(any(Usuario.class));
    }

    @Test
    void deberiaListarUsuarios() {
        // Given
        when(repo.findAll()).thenReturn(List.of(usuarioEjemplo()));
        when(mascotaClient.obtenerMascota(any(), anyString())).thenReturn(mascotaEjemplo());

        // When
        List<UsuarioResponse> resultado = service.listar("Bearer token");

        // Then
        assertEquals(1, resultado.size());
        verify(repo).findAll();
    }

    @Test
    void deberiaObtenerUsuarioPorId() {
        // Given
        when(repo.findById(1L)).thenReturn(Optional.of(usuarioEjemplo()));
        when(mascotaClient.obtenerMascota(any(), anyString())).thenReturn(mascotaEjemplo());

        // When
        UsuarioResponse resultado = service.obtener(1L, "Bearer token");

        // Then
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Perez", resultado.getNombre());
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoExiste() {
        // Given
        when(repo.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(EntityNotFoundException.class, () -> service.obtener(99L, "Bearer token"));
    }

    @Test
    void deberiaActualizarUsuarioCuandoExiste() {
        // Given
        UsuariosDTO dto = usuarioDTOEjemplo();
        when(repo.findById(1L)).thenReturn(Optional.of(usuarioEjemplo()));
        when(mascotaClient.obtenerMascota(eq(2), anyString())).thenReturn(mascotaEjemplo());
        when(repo.save(any(Usuario.class))).thenReturn(usuarioEjemplo());

        // When
        UsuarioResponse resultado = service.actualizar(1L, dto, "Bearer token");

        // Then
        assertEquals("Juan Perez", resultado.getNombre());
        verify(repo).save(any(Usuario.class));
    }

    @Test
    void deberiaLanzarExcepcionAlActualizarUsuarioInexistente() {
        // Given
        when(repo.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(EntityNotFoundException.class,
                () -> service.actualizar(99L, usuarioDTOEjemplo(), "Bearer token"));
    }

    @Test
    void deberiaEliminarUsuario() {
        // When
        service.eliminar(1L);

        // Then
        verify(repo).deleteById(1L);
    }
}
