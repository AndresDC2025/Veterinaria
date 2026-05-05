package com.example.Veterinarios.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Veterinarios.model.Veterinarios;
import com.example.Veterinarios.service.VeterinariosService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/veterinarios")
public class VeterinariosController {

    @Autowired
    private VeterinariosService service;

    @GetMapping("/{id}")
    public Veterinarios getById(@PathVariable Long id) {
        return service.getById(id);
    }
    
}
