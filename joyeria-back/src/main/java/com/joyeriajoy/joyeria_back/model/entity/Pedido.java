package com.joyeriajoy.joyeria_back.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado_pedido", nullable = false)
    private EstadoPedido estadoPedido;

    @Builder.Default
    @Column(name = "kilometros", nullable = false, precision = 10, scale = 2)
    private BigDecimal kilometros = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "costo_delivery", nullable = false, precision = 12, scale = 2)
    private BigDecimal costoDelivery = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "ganancia_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal gananciaTotal = BigDecimal.ZERO;

    @Column(name = "fecha_pedido", updatable = false)
    private LocalDateTime fechaPedido;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "fecha_entrega")
    private java.time.LocalDate fechaEntrega;

    @Column(name = "rango_horario", length = 50)
    private String rangoHorario;

    @Column(name = "tipo_pago", length = 50)
    private String tipoPago; // EFECTIVO, TRANSFERENCIA, MIXTO

    @Builder.Default
    @Column(name = "efectivo", precision = 12, scale = 2)
    private BigDecimal efectivo = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "transferencia", precision = 12, scale = 2)
    private BigDecimal transferencia = BigDecimal.ZERO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PedidoDetalle> detalles = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        fechaPedido = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
