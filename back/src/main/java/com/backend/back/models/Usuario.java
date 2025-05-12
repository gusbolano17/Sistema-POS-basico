package com.backend.back.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Usuario extends Persona{

    private String username;
    private String password;
    private Date fechaCreacion;

}
