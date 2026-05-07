package com.joyeriajoy.joyeria_back.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
    
    private BigDecimal ventasDelDia;
    private BigDecimal gananciaDelDia;
    private Long pedidosPendientes;
}
