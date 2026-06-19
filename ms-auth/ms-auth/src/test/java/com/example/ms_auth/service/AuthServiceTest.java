package com.example.ms_auth.service;

import com.example.ms_auth.dto.AuthResponse;
import com.example.ms_auth.dto.LoginRequest;
import com.example.ms_auth.dto.RegisterRequest;
import com.example.ms_auth.model.RefreshToken;
import com.example.ms_auth.model.Usuario;
import com.example.ms_auth.repository.RefreshTokenRepository;
import com.example.ms_auth.repository.UsuarioRepository;
import com.example.ms_auth.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UsuarioRepository usuarioRepo;
    @Mock private RefreshTokenRepository refreshRepo;
    @Mock private PasswordEncoder encoder;
    @Mock private AuthenticationManager authManager;
    @Mock private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService service;

    @Test
    void deberiaRegistrarUsuarioCorrectamente() {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("pablo");
        req.setPassword("pass123");

        when(encoder.encode("pass123")).thenReturn("encoded");
        when(usuarioRepo.save(any(Usuario.class))).thenAnswer(inv -> {
            Usuario u = inv.getArgument(0);
            u.setId(1L);
            return u;
        });
        when(jwtUtil.generarToken("pablo", "ROLE_USER")).thenReturn("access-token");
        when(refreshRepo.save(any(RefreshToken.class))).thenAnswer(inv -> inv.getArgument(0));

        AuthResponse res = service.register(req);

        assertEquals("access-token", res.getAccessToken());
        assertNotNull(res.getRefreshToken());
        verify(usuarioRepo).save(any(Usuario.class));
    }

    @Test
    void deberiaIniciarSesionCorrectamente() {
        LoginRequest req = new LoginRequest();
        req.setUsername("pablo");
        req.setPassword("pass123");

        Usuario user = new Usuario();
        user.setId(1L);
        user.setUsername("pablo");
        user.setRole("ROLE_USER");

        when(usuarioRepo.findByUsername("pablo")).thenReturn(Optional.of(user));
        when(jwtUtil.generarToken("pablo", "ROLE_USER")).thenReturn("access-token");
        when(refreshRepo.save(any(RefreshToken.class))).thenAnswer(inv -> inv.getArgument(0));

        AuthResponse res = service.login(req);

        assertEquals("access-token", res.getAccessToken());
        verify(authManager).authenticate(any());
    }

    @Test
    void deberiaRenovarTokenCorrectamente() {
        String refreshToken = "uuid-refresh-token";
        RefreshToken token = new RefreshToken();
        token.setToken(refreshToken);
        token.setUsername("pablo");

        Usuario user = new Usuario();
        user.setId(1L);
        user.setUsername("pablo");
        user.setRole("ROLE_USER");

        when(refreshRepo.findByToken(refreshToken)).thenReturn(Optional.of(token));
        when(jwtUtil.esValido(refreshToken)).thenReturn(true);
        when(jwtUtil.esRefreshToken(refreshToken)).thenReturn(true);
        when(usuarioRepo.findByUsername("pablo")).thenReturn(Optional.of(user));
        when(jwtUtil.generarToken("pablo", "ROLE_USER")).thenReturn("new-access-token");

        AuthResponse res = service.refresh(refreshToken);

        assertEquals("new-access-token", res.getAccessToken());
        assertEquals(refreshToken, res.getRefreshToken());
    }

    @Test
    void deberiaLanzarExcepcionConRefreshTokenInvalido() {
        when(refreshRepo.findByToken("invalid-token")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.refresh("invalid-token"));
        assertTrue(ex.getMessage().contains("Refresh inválido"));
    }
}
