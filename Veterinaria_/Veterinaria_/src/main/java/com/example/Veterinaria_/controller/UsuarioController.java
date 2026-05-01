package com.example.Veterinaria_.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Veterinaria_.dto.UsuarioResponseDTO;
import com.example.Veterinaria_.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<UsuarioResponseDTO> getMethodName() {
        return service.getAll();
    }
        
}
