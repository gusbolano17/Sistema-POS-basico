package com.backend.back.models.dtos;


import java.util.List;

public record VentaReq(
        String factura,
        String tipoDocCliente,
        String docCliente,
        List<DetalleVentaReq> producto,
        Long usuarioId
) {
}
