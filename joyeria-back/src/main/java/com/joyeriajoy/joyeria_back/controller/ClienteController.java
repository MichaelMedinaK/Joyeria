package com.joyeriajoy.joyeria_back.controller;

import com.joyeriajoy.joyeria_back.dto.cliente.ClienteRequest;
import com.joyeriajoy.joyeria_back.dto.cliente.ClienteResponse;
import com.joyeriajoy.joyeria_back.dto.common.ApiResponse;
import com.joyeriajoy.joyeria_back.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<List<ClienteResponse>>> getAllClientes() {
        List<ClienteResponse> clientes = clienteService.getAllClientes();
        return ResponseEntity.ok(ApiResponse.success(clientes));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<ClienteResponse>> getClienteById(@PathVariable Long id) {
        ClienteResponse cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(ApiResponse.success(cliente));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<ClienteResponse>> createCliente(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse cliente = clienteService.createCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Cliente creado exitosamente", cliente));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<ClienteResponse>> updateCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {
        ClienteResponse cliente = clienteService.updateCliente(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cliente actualizado exitosamente", cliente));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.ok(ApiResponse.success("Cliente eliminado exitosamente", null));
    }
}
