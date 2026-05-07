package com.joyeriajoy.joyeria_back.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    private Long idCliente;
    private String nombre;
    private String telefono;
    private String email;
    private String documento;
    private LocalDateTime fechaCreacion;
}
