package com.backend.back.util;

import com.backend.back.models.dtos.PersonaReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MapperObjects {

    private final ObjectMapper mapper = new ObjectMapper();

    public PersonaReq mapperPersona(Map<String, Object> map) throws IllegalArgumentException {
        return mapper.convertValue(map, PersonaReq.class);
    }

}
