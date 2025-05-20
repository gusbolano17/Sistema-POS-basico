package com.backend.back.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "municipios")
@Data
public class Municipios{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String municipio;
    private String codigo;
    @JoinColumn(name = "departamento_id", referencedColumnName = "id")
    @ManyToOne
    private Departamentos departamentoId;
}
