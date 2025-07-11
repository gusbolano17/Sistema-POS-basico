package com.backend.back.models.dtos;

import java.util.Map;

public record UsuarioDto(
        Map<String, Object> personaId,
        String username,
        String password
) {
}
