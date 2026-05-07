package com.joyeriajoy.joyeria_back.controller;

import com.joyeriajoy.joyeria_back.dto.common.ApiResponse;
import com.joyeriajoy.joyeria_back.dto.dashboard.DashboardStatsResponse;
import com.joyeriajoy.joyeria_back.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<DashboardStatsResponse>> getEstadisticas() {
        DashboardStatsResponse stats = dashboardService.getEstadisticasDelDia();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
}
