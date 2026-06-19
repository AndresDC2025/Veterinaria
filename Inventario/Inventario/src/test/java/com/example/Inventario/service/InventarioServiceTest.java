package com.example.Inventario.service;

import com.example.Inventario.dto.InventarioDTO;
import com.example.Inventario.model.Inventario;
import com.example.Inventario.repository.InventarioRepository;
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
class InventarioServiceTest {

    @Mock private InventarioRepository repositorio;
    @InjectMocks private InventarioService service;

    private InventarioDTO dtoEjemplo() {
        InventarioDTO dto = new InventarioDTO();
        dto.setNombre("Amoxicilina");
        dto.setStock(100);
        dto.setProveedor("Farma Chile");
        return dto;
    }

    private Inventario insumoEjemplo() {
        return Inventario.builder().id(1).nombre("Amoxicilina").stock(100).proveedor("Farma Chile").build();
    }

    @Test
    void deberiaGuardarInsumo() {
        when(repositorio.save(any(Inventario.class))).thenReturn(insumoEjemplo());

        Inventario resultado = service.guardar(dtoEjemplo());

        assertEquals("Amoxicilina", resultado.getNombre());
        assertEquals(100, resultado.getStock());
    }

    @Test
    void deberiaListarInsumos() {
        when(repositorio.findAll()).thenReturn(List.of(insumoEjemplo()));

        assertEquals(1, service.listar().size());
    }

    @Test
    void deberiaObtenerInsumoPorId() {
        when(repositorio.findById(1)).thenReturn(Optional.of(insumoEjemplo()));

        Inventario i = service.obtener(1);

        assertEquals("Amoxicilina", i.getNombre());
    }

    @Test
    void deberiaLanzarExcepcionCuandoInsumoNoExiste() {
        when(repositorio.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.obtener(99));
    }

    @Test
    void deberiaDescontarStock() {
        Inventario insumo = insumoEjemplo();
        when(repositorio.findById(1)).thenReturn(Optional.of(insumo));
        when(repositorio.save(any(Inventario.class))).thenAnswer(inv -> inv.getArgument(0));

        service.descontarStock(1, 30);

        assertEquals(70, insumo.getStock());
    }

    @Test
    void deberiaLanzarExcepcionConStockInsuficiente() {
        Inventario insumo = insumoEjemplo();
        when(repositorio.findById(1)).thenReturn(Optional.of(insumo));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.descontarStock(1, 200));
        assertTrue(ex.getMessage().contains("Stock insuficiente"));
    }

    @Test
    void deberiaEliminarInsumoExistente() {
        when(repositorio.existsById(1)).thenReturn(true);

        service.eliminar(1);

        verify(repositorio).deleteById(1);
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarInsumoInexistente() {
        when(repositorio.existsById(99)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.eliminar(99));
    }
}
