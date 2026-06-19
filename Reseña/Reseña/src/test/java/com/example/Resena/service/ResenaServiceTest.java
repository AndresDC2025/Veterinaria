package com.example.Resena.service;

import com.example.Resena.dto.ResenaDTO;
import com.example.Resena.model.Resena;
import com.example.Resena.repository.ResenaRepository;
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
class ResenaServiceTest {

    @Mock private ResenaRepository repository;
    @InjectMocks private ResenaService service;

    private ResenaDTO dtoEjemplo() {
        ResenaDTO dto = new ResenaDTO();
        dto.setOpinion("Excelente atención");
        dto.setEstrellas(5);
        dto.setVeterinarioId(1L);
        return dto;
    }

    private Resena resenaEjemplo() {
        return Resena.builder()
                .id(1L)
                .opinion("Excelente atención")
                .estrellas(5)
                .veterinarioId(1L)
                .build();
    }

    @Test
    void deberiaGuardarResena() {
        when(repository.save(any(Resena.class))).thenReturn(resenaEjemplo());

        Resena r = service.save(dtoEjemplo());

        assertEquals("Excelente atención", r.getOpinion());
        assertEquals(5, r.getEstrellas());
    }

    @Test
    void deberiaListarResenas() {
        when(repository.findAll()).thenReturn(List.of(resenaEjemplo()));

        assertEquals(1, service.listar().size());
    }

    @Test
    void deberiaObtenerResenaPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(resenaEjemplo()));

        Resena r = service.getById(1L);

        assertEquals("Excelente atención", r.getOpinion());
    }

    @Test
    void deberiaLanzarExcepcionCuandoResenaNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getById(99L));
    }

    @Test
    void deberiaActualizarResena() {
        ResenaDTO dto = dtoEjemplo();
        dto.setOpinion("Muy buena atención");
        Resena existente = resenaEjemplo();

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Resena.class))).thenAnswer(inv -> inv.getArgument(0));

        Resena resultado = service.actualizar(1L, dto);

        assertEquals("Muy buena atención", resultado.getOpinion());
    }

    @Test
    void deberiaEliminarResenaExistente() {
        when(repository.existsById(1L)).thenReturn(true);

        service.eliminar(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarResenaInexistente() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.eliminar(99L));
    }

    @Test
    void deberiaListarResenasPorVeterinario() {
        when(repository.findByVeterinarioId(1L)).thenReturn(List.of(resenaEjemplo()));

        List<Resena> resultado = service.listarPorVeterinario(1L);

        assertEquals(1, resultado.size());
    }
}
