package com.joyeriajoy.joyeria_back.controller;

import com.joyeriajoy.joyeria_back.dto.common.ApiResponse;
import com.joyeriajoy.joyeria_back.dto.revendedor.RevendedorRequest;
import com.joyeriajoy.joyeria_back.dto.revendedor.RevendedorResponse;
import com.joyeriajoy.joyeria_back.service.RevendedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revendedores")
@RequiredArgsConstructor
public class RevendedorController {

    private final RevendedorService revendedorService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<RevendedorResponse>>> getAllRevendedores() {
        List<RevendedorResponse> revendedores = revendedorService.getAllRevendedores();
        return ResponseEntity.ok(ApiResponse.success(revendedores));
    }

    @GetMapping("/activos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<RevendedorResponse>>> getRevendedoresActivos() {
        List<RevendedorResponse> revendedores = revendedorService.getRevendedoresActivos();
        return ResponseEntity.ok(ApiResponse.success(revendedores));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RevendedorResponse>> getRevendedorById(@PathVariable Long id) {
        RevendedorResponse revendedor = revendedorService.getRevendedorById(id);
        return ResponseEntity.ok(ApiResponse.success(revendedor));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RevendedorResponse>> createRevendedor(@Valid @RequestBody RevendedorRequest request) {
        RevendedorResponse revendedor = revendedorService.createRevendedor(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Revendedor creado exitosamente", revendedor));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RevendedorResponse>> updateRevendedor(
            @PathVariable Long id,
            @Valid @RequestBody RevendedorRequest request) {
        RevendedorResponse revendedor = revendedorService.updateRevendedor(id, request);
        return ResponseEntity.ok(ApiResponse.success("Revendedor actualizado exitosamente", revendedor));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteRevendedor(@PathVariable Long id) {
        revendedorService.deleteRevendedor(id);
        return ResponseEntity.ok(ApiResponse.success("Revendedor desactivado exitosamente", null));
    }
}
