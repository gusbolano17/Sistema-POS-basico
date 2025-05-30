package com.backend.back.models.dtos;

import java.util.Map;

public record UsuarioDto(
        Map<String, String> persona,
        String username,
        String password
) {
}
