package com.example.Historial_Medico.service;

import com.example.Historial_Medico.client.MascotaClient;
import com.example.Historial_Medico.dto.Historial_MedicoDTO;
import com.example.Historial_Medico.dto.Historial_MedicoResponse;
import com.example.Historial_Medico.dto.MascotaResponse;
import com.example.Historial_Medico.model.Historial_Medico;
import com.example.Historial_Medico.repository.Historial_MedicoRepository;
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
class Historial_MedicoServiceTest {

    @Mock private Historial_MedicoRepository repo;
    @Mock private MascotaClient mascotaClient;
    @InjectMocks private Historial_MedicoService service;

    private Historial_MedicoDTO dtoEjemplo() {
        Historial_MedicoDTO dto = new Historial_MedicoDTO();
        dto.setDiagnostico("Gripe canina");
        dto.setTratamiento("Reposo y antibióticos");
        dto.setDescripcion("Mascota con tos y fiebre");
        dto.setIdMascota(1L);
        return dto;
    }

    private Historial_Medico historialEjemplo() {
        Historial_Medico h = new Historial_Medico();
        h.setId(1);
        h.setDiagnostico("Gripe canina");
        h.setTratamiento("Reposo y antibióticos");
        h.setDescripcion("Mascota con tos y fiebre");
        h.setIdMascota(1);
        return h;
    }

    private MascotaResponse mascotaEjemplo() {
        MascotaResponse m = new MascotaResponse();
        m.setId(1);
        m.setNombre("Firulais");
        return m;
    }

    @Test
    void deberiaCrearHistorial() {
        when(mascotaClient.obtenerMascota(anyLong(), anyString())).thenReturn(mascotaEjemplo());
        when(repo.save(any(Historial_Medico.class))).thenReturn(historialEjemplo());

        Historial_MedicoResponse r = service.crear(dtoEjemplo(), "Bearer token");

        assertEquals("Gripe canina", r.getDiagnostico());
    }

    @Test
    void deberiaListarHistoriales() {
        when(repo.findAll()).thenReturn(List.of(historialEjemplo()));
        when(mascotaClient.obtenerMascota(anyLong(), anyString())).thenReturn(mascotaEjemplo());

        List<Historial_MedicoResponse> lista = service.listar("Bearer token");

        assertEquals(1, lista.size());
    }

    @Test
    void deberiaObtenerHistorialPorId() {
        when(repo.findById(1)).thenReturn(Optional.of(historialEjemplo()));
        when(mascotaClient.obtenerMascota(anyLong(), anyString())).thenReturn(mascotaEjemplo());

        Historial_MedicoResponse r = service.obtener(1L, "Bearer token");

        assertEquals("Gripe canina", r.getDiagnostico());
    }

    @Test
    void deberiaLanzarExcepcionCuandoHistorialNoExiste() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(99L, "token"));
    }

    @Test
    void deberiaEliminarHistorial() {
        service.eliminar(1L);

        verify(repo).deleteById(1);
    }
}
