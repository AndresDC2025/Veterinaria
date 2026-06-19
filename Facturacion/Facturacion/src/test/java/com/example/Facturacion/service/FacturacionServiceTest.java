package com.example.Facturacion.service;

import com.example.Facturacion.dto.FacturacionDTO;
import com.example.Facturacion.model.Facturacion;
import com.example.Facturacion.repository.FacturacionRepository;
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
class FacturacionServiceTest {

    @Mock private FacturacionRepository repository;

    @InjectMocks
    private FacturacionService service;

    private FacturacionDTO dtoEjemplo() {
        FacturacionDTO dto = new FacturacionDTO();
        dto.setMonto("25000");
        dto.setMetodoP("Tarjeta");
        return dto;
    }

    private Facturacion facturaEjemplo() {
        Facturacion f = new Facturacion();
        f.setId(1);
        f.setMonto("25000");
        f.setMetodoP("Tarjeta");
        return f;
    }

    @Test
    void deberiaCrearFactura() {
        when(repository.save(any(Facturacion.class))).thenReturn(facturaEjemplo());

        Facturacion resultado = service.crear(dtoEjemplo(), "Bearer token");

        assertEquals("25000", resultado.getMonto());
        assertEquals("Tarjeta", resultado.getMetodoP());
        verify(repository).save(any(Facturacion.class));
    }

    @Test
    void deberiaListarFacturas() {
        when(repository.findAll()).thenReturn(List.of(facturaEjemplo()));

        List<Facturacion> lista = service.listar();

        assertEquals(1, lista.size());
    }

    @Test
    void deberiaObtenerFacturaPorId() {
        when(repository.findById(1)).thenReturn(Optional.of(facturaEjemplo()));

        Facturacion f = service.obtener(1);

        assertEquals(1, f.getId());
        assertEquals("25000", f.getMonto());
    }

    @Test
    void deberiaLanzarExcepcionCuandoFacturaNoExiste() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.obtener(99));
        assertTrue(ex.getMessage().contains("99"));
    }

    @Test
    void deberiaActualizarFactura() {
        FacturacionDTO dto = new FacturacionDTO();
        dto.setMonto("30000");
        dto.setMetodoP("Efectivo");
        Facturacion existente = facturaEjemplo();

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(repository.save(any(Facturacion.class))).thenAnswer(inv -> inv.getArgument(0));

        Facturacion resultado = service.actualizar(1, dto, "Bearer token");

        assertEquals("30000", resultado.getMonto());
        assertEquals("Efectivo", resultado.getMetodoP());
    }

    @Test
    void deberiaEliminarFactura() {
        when(repository.findById(1)).thenReturn(Optional.of(facturaEjemplo()));

        service.eliminar(1);

        verify(repository).delete(any(Facturacion.class));
    }
}
