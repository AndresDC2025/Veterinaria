package com.example.Facturacion.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.Facturacion.client.UsuarioClient;
import com.example.Facturacion.dto.FacturacionDTO;
import com.example.Facturacion.model.Facturacion;
import com.example.Facturacion.repository.FacturacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
@Builder
public class FacturacionService {

    private final FacturacionRepository repo;
    private final UsuarioClient usuarioClient;

    public Facturacion crear(FacturacionDTO dto, String token) {
        log.info("Creando Factura", keyValue("monto", dto.getMonto()), keyValue("usuarioId", dto.getUsuarioId()));

        // Validar que el usuario exista en el microservicio de Usuarios
        var usuario = usuarioClient.obtenerUsuario(dto.getUsuarioId(), token);

        if (usuario == null) {
            log.error("Error al crear factura: Usuario no encontrado", keyValue("usuarioId", dto.getUsuarioId()));
            throw new RuntimeException("El usuario con ID " + dto.getUsuarioId() + " no existe.");
        }

        Facturacion factura = new Facturacion();
        factura.setMonto(dto.getMonto());
        factura.setUsuarioId(dto.getUsuarioId());
        factura.setDetalle(dto.getDetalle());

        return repo.save(factura);
    }

    public List<Facturacion> listar() {
        log.info("Listando todas las facturas");
        return repo.findAll();
    }

    public Facturacion obtener(Integer id) {
        Optional<Facturacion> optionalFactura = repo.findById(id);
        
        if (optionalFactura.isEmpty()) {
            log.error("Factura no encontrada", keyValue("id", id));
            throw new EntityNotFoundException("Factura no encontrada");
        }
        
        return optionalFactura.get();
    }

    public Facturacion actualizar(Integer id, FacturacionDTO dto, String token) {
        Optional<Facturacion> optionalFactura = repo.findById(id);
        
        if (optionalFactura.isEmpty()) {
            log.error("Factura no encontrada para actualizar", keyValue("id", id));
            throw new EntityNotFoundException("Factura no encontrada");
        }

        Facturacion facturaExistente = optionalFactura.get();

        // Validar usuario si se intenta cambiar en la factura
        var usuario = usuarioClient.obtenerUsuario(dto.getUsuarioId(), token);
        if (usuario == null) {
            log.error("Usuario no encontrado para actualizar factura", keyValue("usuarioId", dto.getUsuarioId()));
            throw new RuntimeException("Usuario no encontrado para actualizar la factura");
        }

        facturaExistente.setMonto(dto.getMonto());
        facturaExistente.setUsuarioId(dto.getUsuarioId());
        facturaExistente.setDetalle(dto.getDetalle());

        log.info("Factura actualizada", keyValue("id", id));
        return repo.save(facturaExistente);
    }

    public void eliminar(Integer id) {
        log.info("Eliminando factura", keyValue("id", id));
        // Verificamos existencia antes de borrar para evitar excepciones genéricas
        if (!repo.existsById(id)) {
            log.error("No se pudo eliminar: Factura no encontrada", keyValue("id", id));
            throw new EntityNotFoundException("Factura no encontrada");
        }
        repo.deleteById(id);
    }
}