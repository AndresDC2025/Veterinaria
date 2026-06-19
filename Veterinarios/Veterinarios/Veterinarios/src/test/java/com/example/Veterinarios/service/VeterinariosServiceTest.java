package com.example.Veterinarios.service;

import com.example.Veterinarios.client.ResenaClient;
import com.example.Veterinarios.dto.VeterinarioDTO;
import com.example.Veterinarios.model.Veterinarios;
import com.example.Veterinarios.repository.VeterinariosRepository;
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
class VeterinariosServiceTest {

    @Mock private VeterinariosRepository repositorio;
    @Mock private ResenaClient resenaClient;
    @InjectMocks private VeterinariosService service;

    private VeterinarioDTO dtoEjemplo() {
        VeterinarioDTO dto = new VeterinarioDTO();
        dto.setNombre("Dr. Soto");
        dto.setEspecialidad("Cirugía");
        dto.setHorario("Lunes-Viernes 09:00-18:00");
        dto.setEmail("soto@veterinaria.cl");
        return dto;
    }

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
    void deberiaGuardarVeterinario() {
        when(repositorio.save(any(Veterinarios.class))).thenReturn(vetEjemplo());

        Veterinarios v = service.guardar(dtoEjemplo());

        assertEquals("Dr. Soto", v.getNombre());
        assertEquals("Cirugía", v.getEspecialidad());
    }

    @Test
    void deberiaListarVeterinarios() {
        when(repositorio.findAll()).thenReturn(List.of(vetEjemplo()));

        assertEquals(1, service.listar().size());
    }

    @Test
    void deberiaObtenerVeterinarioPorId() {
        when(repositorio.findById(1L)).thenReturn(Optional.of(vetEjemplo()));

        Veterinarios v = service.obtenerEntity(1L);

        assertEquals("Dr. Soto", v.getNombre());
    }

    @Test
    void deberiaLanzarExcepcionCuandoVeterinarioNoExiste() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtenerEntity(99L));
    }

    @Test
    void deberiaActualizarVeterinario() {
        VeterinarioDTO dto = dtoEjemplo();
        dto.setNombre("Dr. Astudillo");
        Veterinarios existente = vetEjemplo();

        when(repositorio.findById(1L)).thenReturn(Optional.of(existente));
        when(repositorio.save(any(Veterinarios.class))).thenAnswer(inv -> inv.getArgument(0));

        Veterinarios resultado = service.actualizar(1L, dto);

        assertEquals("Dr. Astudillo", resultado.getNombre());
    }

    @Test
    void deberiaEliminarVeterinarioExistente() {
        when(repositorio.existsById(1L)).thenReturn(true);

        service.eliminar(1L);

        verify(repositorio).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarVeterinarioInexistente() {
        when(repositorio.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.eliminar(99L));
    }
}
