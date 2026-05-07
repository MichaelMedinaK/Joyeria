package com.joyeriajoy.joyeria_back.service;

import com.joyeriajoy.joyeria_back.dto.dashboard.DashboardStatsResponse;
import com.joyeriajoy.joyeria_back.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final PedidoRepository pedidoRepository;

    @Transactional(readOnly = true)
    public DashboardStatsResponse getEstadisticasDelDia() {
        log.info("Calculando estadísticas del día");
        
        // Obtener venta total del día
        Double ventasDelDia = pedidoRepository.calcularVentaDelDia();
        
        // Obtener ganancia total del día
        Double gananciaDelDia = pedidoRepository.calcularGananciaDiaria();
        
        // Obtener cantidad de pedidos pendientes
        Long pedidosPendientes = pedidoRepository.contarPedidosPendientesDelDia();
        
        return DashboardStatsResponse.builder()
                .ventasDelDia(BigDecimal.valueOf(ventasDelDia != null ? ventasDelDia : 0.0))
                .gananciaDelDia(BigDecimal.valueOf(gananciaDelDia != null ? gananciaDelDia : 0.0))
                .pedidosPendientes(pedidosPendientes != null ? pedidosPendientes : 0L)
                .build();
    }
}
