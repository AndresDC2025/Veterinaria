package com.example.Historial_Medico.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.Historial_Medico.client.MascotaClient;
import com.example.Historial_Medico.dto.Historial_MedicoDTO;
import com.example.Historial_Medico.model.Historial_Medico;
import com.example.Historial_Medico.repository.Historial_MedicoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class Historial_MedicoService {

    private final Historial_MedicoRepository repo;
    private final MascotaClient mascotaClient;

    public Historial_Medico crear(Historial_Medico dto, String token) {
        log.info("Iniciando creación de Historial Médico", 
            keyValue("mascotaId", dto.getIdMascota()));

        // Validación externa: Consultar al microservicio de Mascotas (Puerto 8085)
        var mascota = mascotaClient.obtenerMascota(dto.getIdMascota(), token);

        if (mascota == null) {
            log.error("Fallo al crear historial: Mascota inexistente", 
                keyValue("mascotaId", dto.getIdMascota()));
            throw new RuntimeException("La mascota con ID " + dto.getIdMascota() + " no existe en el sistema.");
        }

        log.info("Mascota validada exitosamente", keyValue("nombreMascota", mascota.getNombre()));
        return repo.save(dto);
    }

    public List<Historial_Medico> listar() {
        log.info("Listando todos los historiales médicos registrados");
        return repo.findAll();
    }

    public Historial_Medico obtener(Integer id) {
        Optional<Historial_Medico> optionalHistorial = repo.findById(id);
        
        if (optionalHistorial.isEmpty()) {
            log.error("Búsqueda fallida: Historial no encontrado", keyValue("id", id));
            throw new EntityNotFoundException("Historial médico con ID " + id + " no encontrado.");
        }
        
        return optionalHistorial.get();
    }

    public Historial_Medico actualizar(Integer id, Historial_Medico entidad, String token) {
        Optional<Historial_Medico> optionalHistorial = repo.findById(id);
        
        if (optionalHistorial.isEmpty()) {
            log.error("Actualización fallida: Registro inexistente", keyValue("id", id));
            throw new EntityNotFoundException("No se puede actualizar: Historial no encontrado.");
        }

        // Validar si el nuevo ID de mascota es válido antes de actualizar
        var mascota = mascotaClient.obtenerMascota(entidad.getIdMascota(), token);
        if (mascota == null) {
            log.error("Actualización fallida: Nueva mascota no encontrada", 
                keyValue("mascotaId", entidad.getIdMascota()));
            throw new RuntimeException("Mascota no encontrada para el historial.");
        }

        Historial_Medico existente = optionalHistorial.get();
        existente.setFecha(entidad.getFecha());
        existente.setDescripcion(entidad.getDescripcion());
        existente.setDiagnostico(entidad.getDiagnostico());
        existente.setTratamiento(entidad.getTratamiento());
        existente.setIdMascota(entidad.getIdMascota());

        log.info("Historial médico actualizado correctamente", keyValue("id", id));
        return repo.save(existente);
    }

    public void eliminar(Integer id) {
        log.info("Intentando eliminar historial médico", keyValue("id", id));
        
        if (!repo.existsById(id)) {
            log.error("Eliminación fallida: ID no encontrado", keyValue("id", id));
            throw new EntityNotFoundException("No se encontró el registro para eliminar.");
        }
        
        repo.deleteById(id);
        log.info("Historial médico eliminado", keyValue("id", id));
    }
}