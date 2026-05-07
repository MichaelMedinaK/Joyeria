package com.joyeriajoy.joyeria_back.service;

import com.joyeriajoy.joyeria_back.dto.auth.LoginRequest;
import com.joyeriajoy.joyeria_back.dto.auth.LoginResponse;
import com.joyeriajoy.joyeria_back.dto.auth.RegisterRequest;
import com.joyeriajoy.joyeria_back.dto.auth.UpdateUsuarioRequest;
import com.joyeriajoy.joyeria_back.exception.BadRequestException;
import com.joyeriajoy.joyeria_back.exception.ResourceNotFoundException;
import com.joyeriajoy.joyeria_back.model.entity.Rol;
import com.joyeriajoy.joyeria_back.model.entity.Usuario;
import com.joyeriajoy.joyeria_back.repository.RolRepository;
import com.joyeriajoy.joyeria_back.repository.UsuarioRepository;
import com.joyeriajoy.joyeria_back.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public LoginResponse register(RegisterRequest request) {
        log.info("Registrando nuevo usuario: {}", request.getEmail());

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }

        // Buscar rol por código
        Rol rol = rolRepository.findByCodigo(request.getRol().toUpperCase())
                .orElseThrow(() -> new BadRequestException("Rol inválido. Debe ser ADMIN, VENDEDOR o REVENDEDOR"));

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .rol(rol)
                .activo(true)
                .build();

        usuario = usuarioRepository.save(usuario);
        log.info("Usuario registrado exitosamente con ID: {}", usuario.getIdUsuario());

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().getCodigo());

        return LoginResponse.builder()
                .token(token)
                .tipo("Bearer")
                .idUsuario(usuario.getIdUsuario())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol().getCodigo())
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
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().getCodigo());

        log.info("Login exitoso para: {}", request.getEmail());

        return LoginResponse.builder()
                .token(token)
                .tipo("Bearer")
                .idUsuario(usuario.getIdUsuario())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol().getCodigo())
                .build();
    }

    @Transactional
    public LoginResponse updateUsuario(Long idUsuario, UpdateUsuarioRequest request, String emailUsuarioAutenticado) {
        log.info("Actualizando usuario ID: {} (solicitado por: {})", idUsuario, emailUsuarioAutenticado);

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", idUsuario));

        // Validar que el usuario autenticado sea el mismo que se intenta modificar
        Usuario usuarioAutenticado = usuarioRepository.findByEmail(emailUsuarioAutenticado)
                .orElseThrow(() -> new BadRequestException("Usuario autenticado no encontrado"));

        // Solo el mismo usuario o un ADMIN pueden modificar
        if (!usuario.getIdUsuario().equals(usuarioAutenticado.getIdUsuario()) && 
            !"ADMIN".equals(usuarioAutenticado.getRol().getCodigo())) {
            throw new BadRequestException("No tienes permisos para modificar este usuario");
        }

        // Actualizar solo los campos proporcionados
        if (request.getNombre() != null && !request.getNombre().trim().isEmpty()) {
            usuario.setNombre(request.getNombre());
            log.info("Actualizando nombre a: {}", request.getNombre());
        }

        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            // Verificar que el email no esté en uso por otro usuario
            if (!request.getEmail().equals(usuario.getEmail()) && 
                usuarioRepository.existsByEmail(request.getEmail())) {
                throw new BadRequestException("El email ya está registrado");
            }
            usuario.setEmail(request.getEmail());
            log.info("Actualizando email a: {}", request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            log.info("Actualizando contraseña");
        }

        usuario = usuarioRepository.save(usuario);
        log.info("Usuario actualizado exitosamente");

        // Generar nuevo token con el email actualizado
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().getCodigo());

        return LoginResponse.builder()
                .token(token)
                .tipo("Bearer")
                .idUsuario(usuario.getIdUsuario())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol().getCodigo())
                .build();
    }
}
