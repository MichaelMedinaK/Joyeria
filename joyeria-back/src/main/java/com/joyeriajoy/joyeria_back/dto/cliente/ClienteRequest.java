package com.joyeriajoy.joyeria_back.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
    private String nombre;

    @Size(max = 50, message = "El teléfono no puede exceder 50 caracteres")
    private String telefono;

    @Email(message = "Formato de email inválido")
    @Size(max = 150, message = "El email no puede exceder 150 caracteres")
    private String email;

    @Size(max = 50, message = "El documento no puede exceder 50 caracteres")
    private String documento;
}
