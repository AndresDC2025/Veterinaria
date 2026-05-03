package com.example.Veterinaria_.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.example.Veterinaria_.dto.UsuarioResponseDTO;
import com.example.Veterinaria_.model.Usuario;
import com.example.Veterinaria_.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;  

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    public List<UsuarioResponseDTO> getAll(){
        List<UsuarioResponseDTO> listaUsuarios = new ArrayList<>();

        for(Usuario l: repository.findAll()){
            UsuarioResponseDTO usuario = new UsuarioResponseDTO();
            usuario.setId(l.getId());
            usuario.setNombre(l.getNombre());
            usuario.setRut(l.getRut());
            usuario.setEmail(l.getEmail());
            usuario.setTelefono(l.getTelefono());
            usuario.setDireccion(l.getDireccion());

            listaUsuarios.add(usuario);
        }
        return listaUsuarios;
    }

    public void deleteByRut(String rut){
    repository.deleteByRut(rut);
    }
    
    public void deleteById(Long id){
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
        response.add(dto);
}

        return response;
    }

public UsuarioResponseDTO crearUsuario(UsuarioResponseDTO datos) {
    // 1. Creamos la entidad Usuario (Modelo)
    Usuario nuevoUsuario = new Usuario();
    
    // 2. Pasamos los datos del DTO al Modelo
    nuevoUsuario.setNombre(datos.getNombre());
    nuevoUsuario.setRut(datos.getRut());
    nuevoUsuario.setEmail(datos.getEmail());
    nuevoUsuario.setTelefono(datos.getTelefono());
    nuevoUsuario.setDireccion(datos.getDireccion());

    // 3. Guardamos en la base de datos (HeidiSQL)
    Usuario usuarioGuardado = repository.save(nuevoUsuario);

    // 4. Retornamos el DTO de respuesta (que ahora incluye el ID generado)
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