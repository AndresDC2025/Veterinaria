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
    
    
}
    

