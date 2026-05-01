package com.example.Veterinaria_.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Veterinaria_.dto.UsuarioResponseDTO;
import com.example.Veterinaria_.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<UsuarioResponseDTO> getAll(){
        return service.getAll();
    }

    @DeleteMapping("/rut/{rut}")
        public void deleteByRut(@PathVariable String rut){
        service.deleteByRut(rut);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.deleteById(id);
    }

    @GetMapping("/nombre/{nombre}")
    public List<UsuarioResponseDTO> getByNombre(@PathVariable String nombre) {
        return service.findByNombre(nombre);
}
    @PostMapping
    public UsuarioResponseDTO save(@RequestBody UsuarioResponseDTO datos) {
        return service.crearUsuario(datos);
    }

    
}
