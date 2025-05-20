package com.backend.back.models.dtos;

import com.backend.back.models.Departamentos;
import com.backend.back.models.Municipios;

import java.util.Date;

public record PersonaReq(
        String nombre,
        String apellido ,
        String email,
        String tipoDocumento,
        String documento,
        String telefono,
        String direccion,
        Date fechaNacimiento,
        String pais,
        Departamentos departamento,
        Municipios ciudad
) {
}
