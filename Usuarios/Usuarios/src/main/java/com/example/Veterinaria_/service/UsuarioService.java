package com.example.Veterinaria_.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.Veterinaria_.client.MascotaClient;
import com.example.Veterinaria_.dto.UsuarioResponse;
import com.example.Veterinaria_.dto.UsuariosDTO;
import com.example.Veterinaria_.model.Usuario;
import com.example.Veterinaria_.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
@Builder
public class UsuarioService {

    private final UsuarioRepository repo;
    private final MascotaClient mascotaClient;

    public UsuarioResponse crear(UsuariosDTO dto, String token) {
        log.info("Creando Usuario", keyValue("nombre", dto.getNombre()));

        // Validar que la mascota exista en el otro microservicio
        var mascota = mascotaClient.obtenerMascota(dto.getId_mascota(), token);

        if (mascota == null) {
            throw new RuntimeException("La mascota con ID " + dto.getId_mascota() + " no existe.");
        }

        Usuario usuario = repo.save(
                new Usuario(null, dto.getNombre(), dto.getRut(), dto.getEmail(), 
                            dto.getTelefono(), dto.getDireccion(), dto.getId_mascota())
        );

        return mapToResponse(usuario, token);
    }

    public List<UsuarioResponse> listar(String token) {
        return repo.findAll()
                .stream()
                .map(u -> mapToResponse(u, token))
                .toList();
    }

    public UsuarioResponse obtener(Long id, String token) {
        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        return mapToResponse(usuario, token);
    }

    public UsuarioResponse actualizar(Long id, UsuariosDTO dto, String token) {
        Usuario usuarioExistente = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Validar nueva mascota si se cambió el ID
        var mascota = mascotaClient.obtenerMascota(dto.getId_mascota(), token);
        if (mascota == null) {
            throw new RuntimeException("Mascota no encontrada");
        }

        usuarioExistente.setNombre(dto.getNombre());
        usuarioExistente.setRut(dto.getRut());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setTelefono(dto.getTelefono());
        usuarioExistente.setDireccion(dto.getDireccion());
        usuarioExistente.setId_mascota(dto.getId_mascota());

        return mapToResponse(repo.save(usuarioExistente), token);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    // Dentro de UsuarioService.java
    private UsuarioResponse mapToResponse(Usuario usuario, String token) {
        // Se llama al cliente pasando el ID de la mascota y el token de sesión
        var mascota = mascotaClient.obtenerMascota(usuario.getId_mascota(), token);

        return UsuarioResponse.builder()
            .id(usuario.getId())
            .nombre(usuario.getNombre())
            .rut(usuario.getRut())
            .email(usuario.getEmail())
            .telefono(usuario.getTelefono())
            .direccion(usuario.getDireccion())
            .mascota(mascota) // Ahora recibirá el objeto en lugar de null
            .build();
    }
}