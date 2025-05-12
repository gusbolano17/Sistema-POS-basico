package com.backend.back.models;

import com.backend.back.models.enums.EstadoVentas;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "ventas")
@Data
public class Ventas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String factura;
    private Date fechaEmision;
    private Date fechaVencimiento;
    private Double valorTotal;
    @Enumerated(EnumType.STRING)
    private EstadoVentas estado;
    private Date fechaModificacion;
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    @ManyToOne
    private Persona clienteId;
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuarioId;

}
