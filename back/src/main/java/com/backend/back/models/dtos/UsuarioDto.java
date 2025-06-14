package com.backend.back.models.dtos;

import java.util.Map;

public record UsuarioDto(
        Map<String, String> personaId,
        String username,
        String password
) {
}
