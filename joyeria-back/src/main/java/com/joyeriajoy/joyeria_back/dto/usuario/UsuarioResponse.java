package com.joyeriajoy.joyeria_back.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    private Long idUsuario;
    private String nombre;
    private String email;
    private String rol;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
}
