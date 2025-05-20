package com.backend.back.service;

import com.backend.back.models.Departamentos;
import com.backend.back.repository.IDepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {

    @Autowired
    private IDepartamentoRepository departamentoRepository;

    public List<Departamentos> listarDepartamentos() throws Exception {
        return departamentoRepository.findAll();
    }

    public Optional<Departamentos> obtenerPorNombre(String departamento) {
        return departamentoRepository.obtenerPorNombre(departamento);
    }
}
