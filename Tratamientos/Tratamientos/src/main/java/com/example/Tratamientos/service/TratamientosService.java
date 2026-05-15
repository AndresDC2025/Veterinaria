package com.example.Tratamientos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Tratamientos.client.InventarioClient;
import com.example.Tratamientos.dto.InventarioResponse;
import com.example.Tratamientos.dto.TratamientosDTO;
import com.example.Tratamientos.dto.TratamientosResponse;
import com.example.Tratamientos.model.Tratamientos;
import com.example.Tratamientos.repository.TratamientosRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class TratamientosService {

    private final TratamientosRepository repo;
    private final InventarioClient inventarioClient;

<<<<<<< HEAD

    public TratamientosResponse crear(TratamientosDTO dto, String token) {

        log.info("Creando tratamiento",
                keyValue("nombre", dto.getNombre()),
                keyValue("inventarioId", dto.getInventarioId()));

        Tratamientos tratamiento = new Tratamientos();
=======
    public List<Tratamientos> listar() {
        return repository.findAll();
    }

    public Tratamientos getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tratamiento no encontrado"));
    }

    public Tratamientos save(TratamientosDTO dto) {

        Tratamientos tratamiento = Tratamientos.builder()
                .nombre(dto.getNombre())
                .dosis(dto.getDosis())
                .duracion(dto.getDuracion())
                .mascotaId(dto.getMascotaId())
                .build();

        return repository.save(tratamiento);
    }

    public Tratamientos actualizar(Long id, TratamientosDTO dto) {

        Tratamientos tratamiento = getById(id);
>>>>>>> aa98e270dfe1e4235839e43a47b993446df29bb7

        tratamiento.setNombre(dto.getNombre());
        tratamiento.setDosis(dto.getDosis());
        tratamiento.setDuracion(dto.getDuracion());
<<<<<<< HEAD
        tratamiento.setIdHistorial(dto.getIdHistorial());
        tratamiento.setInventarioId(dto.getInventarioId());
=======
        tratamiento.setMascotaId(dto.getMascotaId());
>>>>>>> aa98e270dfe1e4235839e43a47b993446df29bb7

        Tratamientos guardado = repo.save(tratamiento);

        InventarioResponse inventario =
                inventarioClient.obtenerInventario(
                        dto.getInventarioId(),
                        token);

        if (inventario == null) {

            log.error("Inventario no encontrado",
                    keyValue("inventarioId", dto.getInventarioId()));

            throw new RuntimeException("Inventario no existe");
        }

        return construirResponse(guardado, inventario);
    }

    public List<TratamientosResponse> listar(String token) {

    return repo.findAll()
            .stream()
            .map(t -> {

                InventarioResponse inv =
                        inventarioClient.obtenerInventario(
                                t.getInventarioId(),
                                token);

                return construirResponse(t, inv);
            })
            .collect(Collectors.toList());
}

    public TratamientosResponse obtener(Long id, String token) {

        Tratamientos t = repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Tratamiento no encontrado"));

        InventarioResponse inv =
                inventarioClient.obtenerInventario(
                        t.getInventarioId(),
                        token);

        return construirResponse(t, inv);
    }

    public TratamientosResponse actualizar(Long id, TratamientosDTO dto, String token) {

        Tratamientos t = repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Tratamiento no encontrado"));

        t.setNombre(dto.getNombre());
        t.setDosis(dto.getDosis());
        t.setDuracion(dto.getDuracion());
        t.setIdHistorial(dto.getIdHistorial());
        t.setInventarioId(dto.getInventarioId());

        Tratamientos actualizado = repo.save(t);

        InventarioResponse inv =
                inventarioClient.obtenerInventario(
                        actualizado.getInventarioId(),
                        token);

        log.info("Tratamiento actualizado",
                keyValue("id", id));

        return construirResponse(actualizado, inv);
    }


    public void eliminar(Long id) {

<<<<<<< HEAD
        log.info("Eliminando tratamiento",
                keyValue("id", id));

        if (!repo.existsById(id)) {
=======
        if (!repository.existsById(id)) {
>>>>>>> aa98e270dfe1e4235839e43a47b993446df29bb7
            throw new EntityNotFoundException("Tratamiento no encontrado");
        }

        repo.deleteById(id);
    }

    private TratamientosResponse construirResponse(
            Tratamientos t,
            InventarioResponse inv) {

        return TratamientosResponse.builder()
                .id(t.getId())
                .nombre(t.getNombre())
                .dosis(t.getDosis())
                .duracion(t.getDuracion())
                .idHistorial(t.getIdHistorial())
                .inventario(inv)
                .build();
    }

    public List<Tratamientos> listarPorMascota(Long mascotaId) {
        return repository.findByMascotaId(mascotaId);
    }
}