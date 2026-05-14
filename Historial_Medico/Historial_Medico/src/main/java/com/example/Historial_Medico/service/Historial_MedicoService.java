package com.example.Historial_Medico.service;

import com.example.Historial_Medico.dto.*;
import com.example.Historial_Medico.model.Historial_Medico;
import com.example.Historial_Medico.repository.Historial_MedicoRepository;
import com.example.Historial_Medico.client.MascotaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Historial_MedicoService {

    private final Historial_MedicoRepository repo;
    private final MascotaClient mascotaClient;

    public Historial_MedicoResponse crear(Historial_MedicoDTO dto, String token) {
        
        var mascota = mascotaClient.obtenerMascota(dto.getIdMascota(), token);
        if (mascota == null) {
            throw new RuntimeException("No se puede crear el historial: Mascota no encontrada.");
        }

        
        Historial_Medico entidad = new Historial_Medico();
        entidad.setDiagnostico(dto.getDiagnostico());
        entidad.setTratamiento(dto.getTratamiento());
        entidad.setDescripcion(dto.getObservaciones()); 
        entidad.setIdMascota(dto.getIdMascota().intValue());

        
        Historial_Medico guardado = repo.save(entidad);
        return mapToResponse(guardado, mascota.getNombre());
    }

    public List<Historial_MedicoResponse> listar(String token) {
        return repo.findAll().stream()
                .map(entidad -> {
                    
                    var mascota = mascotaClient.obtenerMascota(entidad.getIdMascota().longValue(), token);
                    String nombre = (mascota != null) ? mascota.getNombre() : "Desconocida";
                    return mapToResponse(entidad, nombre);
                })
                .collect(Collectors.toList());
    }

    public Historial_MedicoResponse obtener(Long id, String token) {
        Historial_Medico entidad = repo.findById(id.intValue())
                .orElseThrow(() -> new EntityNotFoundException("Historial médico no encontrado con ID: " + id));

        var mascota = mascotaClient.obtenerMascota(entidad.getIdMascota().longValue(), token);
        String nombre = (mascota != null) ? mascota.getNombre() : "Desconocida";

        return mapToResponse(entidad, nombre);
    }

    public Historial_MedicoResponse actualizar(Long id, Historial_MedicoDTO dto, String token) {
        Historial_Medico existente = repo.findById(id.intValue())
                .orElseThrow(() -> new EntityNotFoundException("No se puede actualizar: Historial inexistente"));

        
        var mascota = mascotaClient.obtenerMascota(dto.getIdMascota(), token);
        if (mascota == null) {
            throw new RuntimeException("Mascota no encontrada para actualizar el historial");
        }

        
        existente.setDiagnostico(dto.getDiagnostico());
        existente.setTratamiento(dto.getTratamiento());
        existente.setDescripcion(dto.getObservaciones());
        existente.setIdMascota(dto.getIdMascota().intValue());

        Historial_Medico actualizado = repo.save(existente);
        return mapToResponse(actualizado, mascota.getNombre());
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id.intValue())) {
            throw new EntityNotFoundException("No se encontró el registro para eliminar");
        }
        repo.deleteById(id.intValue());
    }

    private Historial_MedicoResponse mapToResponse(Historial_Medico entidad, String nombreMascota) {
        return Historial_MedicoResponse.builder()
                .id(entidad.getId().intValue())
                .diagnostico(entidad.getDiagnostico())
                .tratamiento(entidad.getTratamiento())
                .descripcion(entidad.getDescripcion())
                .mascotaId(entidad.getIdMascota().intValue())
                .build();
    }
}