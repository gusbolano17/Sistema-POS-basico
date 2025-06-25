package com.backend.back.models;

import com.backend.back.models.enums.TipoPersona;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
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
    private String pais;
    private Date fechaNacimiento;
    private Date fechaCreacion;
    private Date fechaModificacion;
    @Enumerated(EnumType.STRING)
    private TipoPersona tipoPersona;
    @JoinColumn(name = "departamento_id", referencedColumnName = "id")
    @ManyToOne
    private Departamentos departamentoId;
    @JoinColumn(name = "ciudadId", referencedColumnName = "id")
    @ManyToOne
    private Municipios ciudadId;

}
