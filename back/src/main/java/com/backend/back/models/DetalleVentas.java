package com.backend.back.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "detalle_ventas")
@Data
public class DetalleVentas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cantidad;
    private Double valorTotal;
    private Date fechaCreacion;
    private Date fechaModificacion;
    @JoinColumn(name = "venta_id", referencedColumnName = "id")
    @ManyToOne
    private Ventas ventaId;
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    @ManyToOne
    private Inventario productoId;

}
