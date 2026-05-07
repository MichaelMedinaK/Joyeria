package com.joyeriajoy.joyeria_back.controller;

import com.joyeriajoy.joyeria_back.dto.common.ApiResponse;
import com.joyeriajoy.joyeria_back.dto.stock.MovimientoStockRequest;
import com.joyeriajoy.joyeria_back.dto.stock.StockRequest;
import com.joyeriajoy.joyeria_back.dto.stock.StockResponse;
import com.joyeriajoy.joyeria_back.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/dueno")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<StockResponse>>> getStockDueno() {
        List<StockResponse> stock = stockService.getStockDueno();
        return ResponseEntity.ok(ApiResponse.success(stock));
    }

    @GetMapping("/revendedor/{idRevendedor}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<StockResponse>>> getStockRevendedor(@PathVariable Long idRevendedor) {
        List<StockResponse> stock = stockService.getStockRevendedor(idRevendedor);
        return ResponseEntity.ok(ApiResponse.success(stock));
    }

    @GetMapping("/bajo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<StockResponse>>> getStockBajo() {
        List<StockResponse> stock = stockService.getStockBajo();
        return ResponseEntity.ok(ApiResponse.success(stock));
    }

    @GetMapping("/consultar")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<StockResponse>> consultarStock(
            @RequestParam Long idProducto,
            @RequestParam(required = false) Long idRevendedor) {
        StockResponse stock = stockService.getStockByProductoAndRevendedor(idProducto, idRevendedor);
        return ResponseEntity.ok(ApiResponse.success(stock));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StockResponse>> createOrUpdateStock(@Valid @RequestBody StockRequest request) {
        StockResponse stock = stockService.createOrUpdateStock(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Stock actualizado exitosamente", stock));
    }

    @PostMapping("/movimiento")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StockResponse>> procesarMovimiento(@Valid @RequestBody MovimientoStockRequest request) {
        StockResponse stock = stockService.procesarMovimiento(request);
        return ResponseEntity.ok(ApiResponse.success("Movimiento procesado exitosamente", stock));
    }

    @PostMapping("/transferir")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> transferirStock(
            @RequestParam Long idProducto,
            @RequestParam Long idRevendedor,
            @RequestParam Integer cantidad) {
        stockService.transferirStockARevendedor(idProducto, idRevendedor, cantidad);
        return ResponseEntity.ok(ApiResponse.success("Stock transferido exitosamente", null));
    }
}
