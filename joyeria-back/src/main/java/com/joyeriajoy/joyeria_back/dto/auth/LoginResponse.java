package com.joyeriajoy.joyeria_back.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String tipo;
    private Long idUsuario;
    private String nombre;
    private String email;
    private String rol;
}
