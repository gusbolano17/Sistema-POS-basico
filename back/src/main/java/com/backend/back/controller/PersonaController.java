package com.backend.back.controller;

import com.backend.back.models.Persona;
import com.backend.back.models.dtos.PersonaReq;
import com.backend.back.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/v1/rest/api/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("/listar")
    public ResponseEntity<?> listarPersonas() throws Exception{
        return personaService.listarPersonas();
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> buscarPersonaPorId(@PathVariable("id") Long id) throws Exception{
        return ResponseEntity.ok(personaService.buscarPersonaPorId(id));
    }

    @GetMapping("/obtener/doc/{tipoDocumento}/{documento}")
    public ResponseEntity<?> buscarPersonaPorDoc(@PathVariable("tipoDocumento") String tipoDocumento, @PathVariable("documento") String documento) throws Exception{
        return personaService.buscarPersonaPorDoc(tipoDocumento, documento);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearPersona(@RequestBody PersonaReq persona) throws Exception {
        return personaService.crearPersona(persona);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarPersona(@RequestBody Persona persona) throws Exception {
        return personaService.actualizarPersona(persona);
    }

}
