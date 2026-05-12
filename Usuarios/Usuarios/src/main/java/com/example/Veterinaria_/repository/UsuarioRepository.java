package com.example.Veterinaria_.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Veterinaria_.model.Usuario;

import jakarta.transaction.Transactional;



public interface UsuarioRepository extends JpaRepository<Usuario, Long>{


}
