package com.example.mascotas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mascotas.model.Mascota;
import com.example.mascotas.service.MascotaService;

@RestController
@RequestMapping("/api/v1/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService service; // Asegúrate de tener inyectado el servicio

    @GetMapping("/{id}")
    public Mascota getById(@PathVariable Long id) {
        // Este método es el que busca la mascota 1 y la devuelve
        return service.getById(id); 
    }
}