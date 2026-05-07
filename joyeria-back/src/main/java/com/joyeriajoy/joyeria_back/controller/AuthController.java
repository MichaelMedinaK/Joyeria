package com.joyeriajoy.joyeria_back.controller;

import com.joyeriajoy.joyeria_back.dto.auth.LoginRequest;
import com.joyeriajoy.joyeria_back.dto.auth.LoginResponse;
import com.joyeriajoy.joyeria_back.dto.auth.RegisterRequest;
import com.joyeriajoy.joyeria_back.dto.common.ApiResponse;
import com.joyeriajoy.joyeria_back.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<LoginResponse>> register(@Valid @RequestBody RegisterRequest request) {
        LoginResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuario registrado exitosamente", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login exitoso", response));
    }
}
