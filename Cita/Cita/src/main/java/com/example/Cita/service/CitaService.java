package com.example.Cita.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.Cita.client.FacturaClient;
import com.example.Cita.dto.CitaDTO;
import com.example.Cita.model.Cita;
import com.example.Cita.repository.CitaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class CitaService {

    private final CitaRepository repo;
    private final FacturaClient facturacionClient; 

    public Cita crear(CitaDTO dto, String token) { 
        log.info("Crear cita y generar facturación", 
            keyValue("usuarioId", dto.getUsuarioId()));

        Cita c = new Cita();
        c.setFecha(dto.getFecha());
        c.setHora(dto.getHora());
        c.setMotivo(dto.getMotivo());
        c.setUsuarioId(dto.getUsuarioId());
        c.setMascotaId(dto.getMascotaId());

        Cita guardada = repo.save(c);

        try {
            facturacionClient.generarFacturaAutomatica(guardada.getUsuarioId(), guardada.getId(), token);
        } catch (Exception e) {
            log.error("No se pudo conectar con Facturación", e);
        }

        return guardada;
    }

    public List<Cita> listar() {
        log.info("Listar citas");
        return repo.findAll();
    }

    public Cita obtener(Long id) {
        log.info("Obtener cita", keyValue("id", id));
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cita no encontrada"));
    }

    public Cita actualizar(Long id, CitaDTO dto) {
        log.info("Actualizar cita", keyValue("id", id));

        Cita c = obtener(id);
        c.setFecha(dto.getFecha());
        c.setHora(dto.getHora());
        c.setMotivo(dto.getMotivo());
        c.setUsuarioId(dto.getUsuarioId());
        
        c.setMascotaId(dto.getMascotaId());

        return repo.save(c);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar cita", keyValue("id", id));
        repo.deleteById(id);
    }
}