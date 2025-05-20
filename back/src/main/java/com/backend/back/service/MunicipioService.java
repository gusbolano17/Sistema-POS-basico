package com.backend.back.service;

import com.backend.back.models.Municipios;
import com.backend.back.repository.ICiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipioService {

    @Autowired
    private ICiudadRepository ciudadRepository;

    public List<Municipios> listarPorDepartamento(String departamento) throws Exception {
        return ciudadRepository.listarPorDepartamento(departamento);
    }

}
