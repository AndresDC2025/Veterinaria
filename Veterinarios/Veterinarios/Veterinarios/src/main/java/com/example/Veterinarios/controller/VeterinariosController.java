package com.example.Veterinarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Veterinarios.dto.VeterinariosResponseDTO;
import com.example.Veterinarios.service.VeterinariosService;

@RestController
@RequestMapping("/api/v1/veterinarios")
public class VeterinariosController {

    @Autowired
    private VeterinariosService service;

    /*  Para crear un nuevo usuario
    @PostMapping
    public VeterinariosResponseDTO save(@RequestBody VeterinariosResponseDTO datos) {
        return service.crearUsuario(datos);
    }
    

    // Para obtener todos los usuarios
    @GetMapping
    public List<VeterinariosResponseDTO> getAll(){
        return service.getAll();    
    }

     Para obtener un usuario por su nombre
    @GetMapping("/nombre/{nombre}")
    public List<VeterinariosResponseDTO> getByNombre(@PathVariable String nombre) {
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
        */
}
