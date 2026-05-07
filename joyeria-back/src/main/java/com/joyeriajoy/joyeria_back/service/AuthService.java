package com.joyeriajoy.joyeria_back.service;

import com.joyeriajoy.joyeria_back.dto.auth.LoginRequest;
import com.joyeriajoy.joyeria_back.dto.auth.LoginResponse;
import com.joyeriajoy.joyeria_back.dto.auth.RegisterRequest;
import com.joyeriajoy.joyeria_back.exception.BadRequestException;
import com.joyeriajoy.joyeria_back.model.entity.Usuario;
import com.joyeriajoy.joyeria_back.repository.UsuarioRepository;
import com.joyeriajoy.joyeria_back.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public LoginResponse register(RegisterRequest request) {
        log.info("Registrando nuevo usuario: {}", request.getEmail());

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }

        Usuario.RolUsuario rol;
        try {
            rol = Usuario.RolUsuario.valueOf(request.getRol().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Rol inválido. Debe ser ADMIN o VENDEDOR");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .rol(rol)
                .activo(true)
                .build();

        usuario = usuarioRepository.save(usuario);
        log.info("Usuario registrado exitosamente con ID: {}", usuario.getIdUsuario());

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());

        return LoginResponse.builder()
                .token(token)
                .tipo("Bearer")
                .idUsuario(usuario.getIdUsuario())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        log.info("Intento de login para: {}", request.getEmail());

        // Autenticar con Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Buscar usuario
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        // Generar token
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());

        log.info("Login exitoso para: {}", request.getEmail());

        return LoginResponse.builder()
                .token(token)
                .tipo("Bearer")
                .idUsuario(usuario.getIdUsuario())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .build();
    }
}
