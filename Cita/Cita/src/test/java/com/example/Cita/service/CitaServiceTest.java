package com.example.Cita.service;

import com.example.Cita.client.FacturaClient;
import com.example.Cita.dto.CitaDTO;
import com.example.Cita.model.Cita;
import com.example.Cita.repository.CitaRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {

    @Mock private CitaRepository repo;
    @Mock private FacturaClient facturacionClient;

    @InjectMocks
    private CitaService service;

    private CitaDTO citaDTOEjemplo() {
        CitaDTO dto = new CitaDTO();
        dto.setFecha("2026-06-20");
        dto.setHora("10:00");
        dto.setMotivo("Control anual");
        dto.setUsuarioId(1);
        dto.setMascotaId(2);
        return dto;
    }

    private Cita citaEjemplo() {
        Cita c = new Cita();
        c.setId(1);
        c.setFecha("2026-06-20");
        c.setHora("10:00");
        c.setMotivo("Control anual");
        c.setUsuarioId(1);
        return c;
    }

    @Test
    void deberiaCrearCitaYDispararFacturacion() {
        CitaDTO dto = citaDTOEjemplo();
        Cita guardada = citaEjemplo();

        when(repo.save(any(Cita.class))).thenReturn(guardada);

        Cita resultado = service.crear(dto, "Bearer token");

        assertEquals(1, resultado.getId());
        assertEquals("Control anual", resultado.getMotivo());
        verify(repo).save(any(Cita.class));
        verify(facturacionClient).generarFacturaAutomatica(any(), any(), any());
    }

    @Test
    void deberiaCrearCitaAunCuandoFacturacionFalla() {
        CitaDTO dto = citaDTOEjemplo();
        Cita guardada = citaEjemplo();

        when(repo.save(any(Cita.class))).thenReturn(guardada);
        doThrow(new RuntimeException("Facturacion no disponible"))
                .when(facturacionClient).generarFacturaAutomatica(any(), any(), any());

        Cita resultado = service.crear(dto, "Bearer token");

        assertNotNull(resultado);
        verify(repo).save(any(Cita.class));
    }

    @Test
    void deberiaListarCitas() {
        when(repo.findAll()).thenReturn(List.of(citaEjemplo()));

        List<Cita> lista = service.listar();

        assertEquals(1, lista.size());
        verify(repo).findAll();
    }

    @Test
    void deberiaObtenerCitaPorId() {
        when(repo.findById(1L)).thenReturn(Optional.of(citaEjemplo()));

        Cita resultado = service.obtener(1L);

        assertEquals(1, resultado.getId());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCitaNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(99L));
    }

    @Test
    void deberiaEliminarCita() {
        service.eliminar(1L);
        verify(repo).deleteById(1L);
    }
}
