package com.backend.back.models.dtos;

import com.backend.back.models.Categoria;

import java.util.Map;

public record InventarioReq(
        String nombre,
        String descripcion,
        Long stock,
        Categoria categoria,
        Double precio,
        Map<String, String> proveedor
) {
}
