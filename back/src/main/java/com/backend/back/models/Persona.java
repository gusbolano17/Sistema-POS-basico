package com.backend.back.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String tipoDocumento;
    private String documento;
    private String telefono;
    private String direccion;
    private Date fechaNacimiento;
    private String pais;
    @JoinColumn(name = "departamento_id", referencedColumnName = "id")
    @ManyToOne
    private Departamentos departamentoId;
    @JoinColumn(name = "ciudadId", referencedColumnName = "id")
    @ManyToOne
    private Municipios ciudadId;

}
