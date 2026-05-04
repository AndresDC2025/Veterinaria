package com.example.Veterinaria_.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.example.Veterinaria_.dto.MascotaDTO;
import com.example.Veterinaria_.dto.UsuarioResponseDTO;
import com.example.Veterinaria_.model.Usuario;
import com.example.Veterinaria_.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;  

    // Configuración del WebClient para realizar peticiones HTTP
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    public List<UsuarioResponseDTO> getAll() {
        List<UsuarioResponseDTO> listaUsuarios = new ArrayList<>();

        for (Usuario u : repository.findAll()) {
            UsuarioResponseDTO usuario = new UsuarioResponseDTO();
            usuario.setId(u.getId());
            usuario.setNombre(u.getNombre());
            usuario.setRut(u.getRut());
            usuario.setEmail(u.getEmail());
            usuario.setTelefono(u.getTelefono());
            usuario.setDireccion(u.getDireccion());
            MascotaDTO mascota = getMascota(u.getId_mascota());
            usuario.setMascota(mascota);

            // LLAMADA AL MICROSERVICIO DE MASCOTAS
            // Se usa el id_mascota guardado en la base de datos de usuarios

            listaUsuarios.add(usuario);
        }
        return listaUsuarios;
    }

    private MascotaDTO getMascota(Integer id) {
        return webClient().get()
        .uri("http://localhost:8085/api/v1/mascotas/" + id)
        .retrieve()
        .bodyToMono(MascotaDTO.class)
        .block();
    }


    

    public void deleteByRut(String rut) {
        repository.deleteByRut(rut);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<UsuarioResponseDTO> findByNombre(String nombre) {
        List<Usuario> lista = repository.findByNombre(nombre);
        List<UsuarioResponseDTO> response = new ArrayList<>();

        for (Usuario u : lista) {
            UsuarioResponseDTO dto = new UsuarioResponseDTO();
            dto.setId(u.getId());
            dto.setNombre(u.getNombre());
            dto.setRut(u.getRut());
            dto.setEmail(u.getEmail());
            dto.setTelefono(u.getTelefono());
            dto.setDireccion(u.getDireccion());
            dto.setMascota(getMascota(u.getId_mascota()));
            response.add(dto);
        }
        return response;
    }

    public UsuarioResponseDTO crearUsuario(UsuarioResponseDTO datos) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(datos.getNombre());
        nuevoUsuario.setRut(datos.getRut());
        nuevoUsuario.setEmail(datos.getEmail());
        nuevoUsuario.setTelefono(datos.getTelefono());
        nuevoUsuario.setDireccion(datos.getDireccion());
        
        // Si el DTO trae una mascota, guardamos su ID (asegúrate de tener este campo en tu modelo)
        if (datos.getMascota() != null) {
            nuevoUsuario.setId_mascota(datos.getMascota().getId().intValue());
        }

        Usuario usuarioGuardado = repository.save(nuevoUsuario);

        UsuarioResponseDTO respuesta = new UsuarioResponseDTO();
        respuesta.setId(usuarioGuardado.getId());
        respuesta.setNombre(usuarioGuardado.getNombre());
        respuesta.setRut(usuarioGuardado.getRut());
        respuesta.setEmail(usuarioGuardado.getEmail());
        respuesta.setTelefono(usuarioGuardado.getTelefono());
        respuesta.setDireccion(usuarioGuardado.getDireccion());
        
        return respuesta;
    }
}