package com.example.Facturacion.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.Facturacion.client.UsuarioClient;
import com.example.Facturacion.dto.FacturacionDTO;
import com.example.Facturacion.dto.FacturaResponse;
import com.example.Facturacion.dto.UsuarioResponse;
import com.example.Facturacion.model.Facturacion;
import com.example.Facturacion.repository.FacturacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacturacionService {

    private final FacturacionRepository repo;
    private final UsuarioClient usuarioClient;

    public FacturaResponse crear(FacturacionDTO dto, Integer usuarioId, Integer idCita, String token) {
        log.info("Creando Factura", 
            keyValue("monto", dto.getMonto()), 
            keyValue("usuarioId", usuarioId),
            keyValue("idCita", idCita));

        UsuarioResponse usuario = usuarioClient.obtenerUsuario(usuarioId, token);
        if (usuario == null) {
            log.error("Error al crear factura: Usuario no encontrado", keyValue("usuarioId", usuarioId));
            throw new RuntimeException("El usuario con ID " + usuarioId + " no existe.");
        }

        Facturacion factura = new Facturacion();
        factura.setMonto(dto.getMonto());
        factura.setMetodoP(dto.getMetodoP());
        factura.setUsuarioId(usuarioId); 
        factura.setIdCita(idCita);      

        Facturacion guardada = repo.save(factura);

        return construirResponse(guardada, usuario);
    }

    public List<FacturaResponse> listar(String token) {
        log.info("Listando todas las facturas");
        return repo.findAll().stream()
                .map(factura -> {
                    UsuarioResponse user = usuarioClient.obtenerUsuario(factura.getUsuarioId(), token);
                    return construirResponse(factura, user);
                })
                .collect(Collectors.toList());
    }

    public FacturaResponse obtener(Integer id, String token) {
        Facturacion factura = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada"));
        
        UsuarioResponse user = usuarioClient.obtenerUsuario(factura.getUsuarioId(), token);
        return construirResponse(factura, user);
    }

    public FacturaResponse actualizar(Integer id, FacturacionDTO dto, String token) {
        Facturacion facturaExistente = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada"));

        facturaExistente.setMonto(dto.getMonto());
        facturaExistente.setMetodoP(dto.getMetodoP());

        Facturacion actualizada = repo.save(facturaExistente);
        UsuarioResponse user = usuarioClient.obtenerUsuario(actualizada.getUsuarioId(), token);

        log.info("Factura actualizada", keyValue("id", id));
        return construirResponse(actualizada, user);
    }

    public void eliminar(Integer id) {
        log.info("Eliminando factura", keyValue("id", id));
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("No se pudo eliminar: Factura no encontrada");
        }
        repo.deleteById(id);
    }

    private FacturaResponse construirResponse(Facturacion entidad, UsuarioResponse usuario) {
        return FacturaResponse.builder()
                .monto(entidad.getMonto())
                .metodoP(entidad.getMetodoP())
                .idCita(entidad.getIdCita())
                .user(usuario)
                .build();
    }
}