package com.backend.back.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Usuario extends Persona{

    private String username;
    @JsonIgnore
    private String password;
    private Date fechaCreacion;

}
