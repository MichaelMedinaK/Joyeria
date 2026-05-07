package com.joyeriajoy.joyeria_back.dto.revendedor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevendedorResponse {

    private Long idRevendedor;
    private String nombre;
    private String telefono;
    private String email;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
}
