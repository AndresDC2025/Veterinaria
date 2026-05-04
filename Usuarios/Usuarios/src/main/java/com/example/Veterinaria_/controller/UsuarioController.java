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

    // Para crear un nuevo usuario
    @PostMapping
    public UsuarioResponseDTO save(@RequestBody UsuarioResponseDTO datos) {
        return service.crearUsuario(datos);
    }

    // Para obtener todos los usuarios
    @GetMapping
    public List<UsuarioResponseDTO> getAll(){
        return service.getAll();    
    }

    // Para obtener un usuario por su nombre
    @GetMapping("/nombre/{nombre}")
    public List<UsuarioResponseDTO> getByNombre(@PathVariable String nombre) {
        return service.findByNombre(nombre);
    }

    // Para eliminar un usuario por su RUT
    @DeleteMapping("/rut/{rut}")
        public void deleteByRut(@PathVariable String rut){
        service.deleteByRut(rut);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.deleteById(id);
    }

}
