package com.example.Tratamientos.service;

import com.example.Tratamientos.client.InventarioClient;
import com.example.Tratamientos.dto.InventarioResponse;
import com.example.Tratamientos.dto.TratamientosDTO;
import com.example.Tratamientos.dto.TratamientosResponse;
import com.example.Tratamientos.model.Tratamientos;
import com.example.Tratamientos.repository.TratamientosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TratamientosServiceTest {

    @Mock private TratamientosRepository repo;
    @Mock private InventarioClient inventarioClient;
    @InjectMocks private TratamientosService service;

    private TratamientosDTO dtoEjemplo() {
        TratamientosDTO dto = new TratamientosDTO();
        dto.setNombre("Amoxicilina 500mg");
        dto.setDosis("2 veces al día");
        dto.setDuracion("7 días");
        dto.setIdHistorial(1);
        dto.setInventarioId(1L);
        return dto;
    }

    private Tratamientos tratamientoEjemplo() {
        Tratamientos t = new Tratamientos();
        t.setId(1L);
        t.setNombre("Amoxicilina 500mg");
        t.setDosis("2 veces al día");
        t.setDuracion("7 días");
        t.setIdHistorial(1);
        t.setInventarioId(1L);
        return t;
    }

    private InventarioResponse inventarioEjemplo() {
        InventarioResponse inv = new InventarioResponse();
        inv.setId(1L);
        inv.setNombre("Amoxicilina");
        inv.setStock(100);
        return inv;
    }

    @Test
    void deberiaCrearTratamiento() {
        when(repo.save(any(Tratamientos.class))).thenReturn(tratamientoEjemplo());
        when(inventarioClient.obtenerInventario(anyLong(), anyString())).thenReturn(inventarioEjemplo());

        TratamientosResponse r = service.crear(dtoEjemplo(), "Bearer token");

        assertEquals("Amoxicilina 500mg", r.getNombre());
        assertNotNull(r.getInventario());
    }

    @Test
    void deberiaListarTratamientos() {
        when(repo.findAll()).thenReturn(List.of(tratamientoEjemplo()));
        when(inventarioClient.obtenerInventario(anyLong(), anyString())).thenReturn(inventarioEjemplo());

        List<TratamientosResponse> lista = service.listar("Bearer token");

        assertEquals(1, lista.size());
    }

    @Test
    void deberiaObtenerTratamientoPorId() {
        when(repo.findById(1L)).thenReturn(Optional.of(tratamientoEjemplo()));
        when(inventarioClient.obtenerInventario(anyLong(), anyString())).thenReturn(inventarioEjemplo());

        TratamientosResponse r = service.obtener(1L, "Bearer token");

        assertEquals("Amoxicilina 500mg", r.getNombre());
    }

    @Test
    void deberiaLanzarExcepcionCuandoTratamientoNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(99L, "token"));
    }

    @Test
    void deberiaEliminarTratamientoExistente() {
        when(repo.existsById(1L)).thenReturn(true);

        service.eliminar(1L);

        verify(repo).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarTratamientoInexistente() {
        when(repo.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.eliminar(99L));
    }
}
