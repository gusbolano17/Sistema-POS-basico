package com.backend.back.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    @JsonIgnore
    private String password;
    private Date fechaCreacion;
    @JoinColumn(name = "personaId", referencedColumnName = "id")
    @ManyToOne
    private Persona personaId;

}
