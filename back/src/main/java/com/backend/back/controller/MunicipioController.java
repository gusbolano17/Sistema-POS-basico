package com.backend.back.controller;

import com.backend.back.service.MunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/v1/rest/api/municipios")
public class MunicipioController {

    @Autowired
    private MunicipioService municipioService;

    @GetMapping("/listar-departamento/{dep}")
    public ResponseEntity<?> listarPorDepartamento(@PathVariable("dep") String departamento) throws Exception{
        return ResponseEntity.ok(municipioService.listarPorDepartamento(departamento));
    }

}
