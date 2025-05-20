package com.backend.back.models.dtos;

public record ResponseDTO<T>(
        String msg,
        String code,
        T body
) {
}
