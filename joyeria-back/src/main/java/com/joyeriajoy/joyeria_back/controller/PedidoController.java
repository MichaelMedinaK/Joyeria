package com.joyeriajoy.joyeria_back.controller;

import com.joyeriajoy.joyeria_back.dto.common.ApiResponse;
import com.joyeriajoy.joyeria_back.dto.pedido.PedidoRequest;
import com.joyeriajoy.joyeria_back.dto.pedido.PedidoResponse;
import com.joyeriajoy.joyeria_back.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> getAllPedidos() {
        List<PedidoResponse> pedidos = pedidoService.getAllPedidos();
        return ResponseEntity.ok(ApiResponse.success(pedidos));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<PedidoResponse>> getPedidoById(@PathVariable Long id) {
        PedidoResponse pedido = pedidoService.getPedidoById(id);
        return ResponseEntity.ok(ApiResponse.success(pedido));
    }

    @GetMapping("/cliente/{idCliente}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> getPedidosByCliente(@PathVariable Long idCliente) {
        List<PedidoResponse> pedidos = pedidoService.getPedidosByCliente(idCliente);
        return ResponseEntity.ok(ApiResponse.success(pedidos));
    }

    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> getPedidosByEstado(@PathVariable String estado) {
        List<PedidoResponse> pedidos = pedidoService.getPedidosByEstado(estado);
        return ResponseEntity.ok(ApiResponse.success(pedidos));
    }

    @GetMapping("/fecha/{fecha}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> getPedidosByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<PedidoResponse> pedidos = pedidoService.getPedidosByFecha(fecha);
        return ResponseEntity.ok(ApiResponse.success(pedidos));
    }

    @GetMapping("/ganancia/diaria")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Double>> getGananciaDiaria() {
        Double ganancia = pedidoService.getGananciaDiaria();
        return ResponseEntity.ok(ApiResponse.success("Ganancia del día", ganancia));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<PedidoResponse>> createPedido(@Valid @RequestBody PedidoRequest request) {
        PedidoResponse pedido = pedidoService.createPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Pedido creado exitosamente", pedido));
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<PedidoResponse>> updateEstadoPedido(
            @PathVariable Long id,
            @RequestParam String estado) {
        PedidoResponse pedido = pedidoService.updateEstadoPedido(id, estado);
        return ResponseEntity.ok(ApiResponse.success("Estado actualizado exitosamente", pedido));
    }
}
