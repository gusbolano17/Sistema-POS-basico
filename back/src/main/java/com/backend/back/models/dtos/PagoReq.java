package com.backend.back.models.dtos;

import com.backend.back.models.enums.MetodoPago;

public record PagoReq(
        Long ventaId,
        MetodoPago metodoPago,
        Double monto
) {
}
