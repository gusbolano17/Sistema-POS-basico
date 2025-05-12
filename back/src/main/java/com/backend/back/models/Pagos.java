package com.backend.back.models;

import com.backend.back.models.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "pagos")
@Data
public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double monto;
    private Date fechaPago;
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;
    @JoinColumn(name = "venta_id", referencedColumnName = "id")
    @ManyToOne
    private Ventas ventaId;

}
