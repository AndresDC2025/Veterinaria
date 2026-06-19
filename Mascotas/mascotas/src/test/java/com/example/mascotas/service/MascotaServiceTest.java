package com.example.mascotas.service;

import com.example.mascotas.client.TratamientoClient;
import com.example.mascotas.dto.MascotaDTO;
import com.example.mascotas.dto.MascotaResponse;
import com.example.mascotas.dto.TratamientoResponse;
import com.example.mascotas.model.Mascota;
import com.example.mascotas.repository.MascotaRepository;
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
class MascotaServiceTest {

    // Nota: MascotaService tiene dos campos inyectados del mismo tipo MascotaRepository
    // ("repository" para el método obtener(id, token) y "repo" para el resto de operaciones CRUD).
    // Se mockean ambos por nombre para que la inyección por constructor de Mockito los resuelva correctamente.
    @Mock private MascotaRepository repository;
    @Mock private MascotaRepository repo;
    @Mock private TratamientoClient tratamientoClient;

    @InjectMocks
    private MascotaService service;

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
    void deberiaCrearMascotaCorrectamente() {
        when(repo.save(any(Mascota.class))).thenReturn(mascotaEjemplo());

        Mascota resultado = service.crear(dtoEjemplo());

        assertEquals("Max", resultado.getNombre());
        assertEquals("Labrador", resultado.getRaza());
        verify(repo).save(any(Mascota.class));
    }

    @Test
    void deberiaListarMascotas() {
        when(repo.findAll()).thenReturn(List.of(mascotaEjemplo()));

        List<Mascota> lista = service.listar();

        assertEquals(1, lista.size());
        assertEquals("Max", lista.get(0).getNombre());
    }

    @Test
    void deberiaObtenerMascotaPorId() {
        when(repo.findById(1L)).thenReturn(Optional.of(mascotaEjemplo()));

        Mascota m = service.obtener(1L);

        assertEquals(1, m.getId());
        assertEquals("Max", m.getNombre());
    }

    @Test
    void deberiaLanzarExcepcionCuandoMascotaNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(99L));
    }

    @Test
    void deberiaActualizarMascota() {
        MascotaDTO dto = dtoEjemplo();
        dto.setNombre("Rex");
        Mascota existente = mascotaEjemplo();

        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(repo.save(any(Mascota.class))).thenAnswer(inv -> inv.getArgument(0));

        Mascota resultado = service.actualizar(1L, dto);

        assertEquals("Rex", resultado.getNombre());
    }

    @Test
    void deberiaEliminarMascota() {
        service.eliminar(1L);
        verify(repo).deleteById(1L);
    }

    @Test
    void deberiaObtenerMascotaConTratamientosPorIdYToken() {
        when(repository.findById(1L)).thenReturn(Optional.of(mascotaEjemplo()));
        TratamientoResponse tratamiento = new TratamientoResponse();
        when(tratamientoClient.listarPorMascota(eq(1L), anyString())).thenReturn(List.of(tratamiento));

        MascotaResponse resultado = service.obtener(1L, "Bearer token");

        assertEquals("Max", resultado.getNombre());
        assertEquals(1, resultado.getTratamientos().size());
    }

    @Test
    void deberiaLanzarExcepcionCuandoMascotaNoExisteAlObtenerConToken() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(java.util.NoSuchElementException.class, () -> service.obtener(99L, "Bearer token"));
    }
}
