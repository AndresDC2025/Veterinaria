package com.example.ms_auth.controller;

import com.example.ms_auth.dto.*;
import com.example.ms_auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Autenticacion", description = "Operaciones de autenticacion y registro de usuarios")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en el sistema")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest req) {
        log.info("POST /auth/register - usuario: {}", req.getUsername());

        AuthResponse res = service.register(req);

        return ResponseEntity.ok(
                ApiResponse.<AuthResponse>builder()
                        .success(true)
                        .message("Usuario registrado")
                        .data(res)
                        .build()
        );
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion", description = "Autentica a un usuario y devuelve un token JWT")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest req) {
        log.info("POST /auth/login - usuario: {}", req.getUsername());
        AuthResponse res = service.login(req);

        return ResponseEntity.ok(
                ApiResponse.<AuthResponse>builder()
                        .success(true)
                        .message("Login exitoso")
                        .data(res)
                        .build()
        );
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovar token", description = "Renueva el token de acceso usando un refresh token")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@RequestBody RefreshRequest req) {

        AuthResponse res = service.refresh(req.getRefreshToken());

        return ResponseEntity.ok(
                ApiResponse.<AuthResponse>builder()
                        .success(true)
                        .message("Token renovado")
                        .data(res)
                        .build()
        );
    }
}