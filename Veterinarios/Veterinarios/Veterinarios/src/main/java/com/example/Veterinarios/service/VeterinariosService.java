package com.example.Veterinarios.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Veterinarios.dto.VeterinarioDTO;
import com.example.Veterinarios.model.Veterinarios;
import com.example.Veterinarios.repository.VeterinariosRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VeterinariosService {

    private final VeterinariosRepository repositorio;

    public List<Veterinarios> listar() {

        return repositorio.findAll();
    }

    public Veterinarios obtener(Long id) {

        return repositorio.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Veterinario no encontrado"
                        ));
    }

    public Veterinarios guardar(VeterinarioDTO dto) {

        log.info("Guardando veterinario");

        Veterinarios veterinario = Veterinarios.builder()
                .nombre(dto.getNombre())
                .especialidad(dto.getEspecialidad())
                .horario(dto.getHorario())
                .email(dto.getEmail())
                .build();

        return repositorio.save(veterinario);
    }

    public Veterinarios actualizar(
            Long id,
            VeterinarioDTO dto
    ) {

        Veterinarios veterinario = obtener(id);

        veterinario.setNombre(dto.getNombre());
        veterinario.setEspecialidad(dto.getEspecialidad());
        veterinario.setHorario(dto.getHorario());
        veterinario.setEmail(dto.getEmail());

        return repositorio.save(veterinario);
    }

    public void eliminar(Long id) {

        if (!repositorio.existsById(id)) {

            throw new EntityNotFoundException(
                    "Veterinario no encontrado"
            );
        }

        repositorio.deleteById(id);
    }
}