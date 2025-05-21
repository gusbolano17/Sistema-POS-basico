package com.backend.back.controller;

import com.backend.back.models.Persona;
import com.backend.back.models.dtos.PersonaReq;
import com.backend.back.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @GetMapping("/listar/nombre/{nombre}")
    public ResponseEntity<?> buscarPersonasPorNombre(@PathVariable("nombre") String nombre) throws Exception{
        return personaService.buscarPersonasPorNombre(nombre);
    }

    @GetMapping("/listar/departamento/{dep}/ciudad/{city}")
    public ResponseEntity<?> buscarPersonaLocacion(@PathVariable("dep") String dep, @PathVariable("city") String city) throws Exception{
        return personaService.buscarPersonaLocacion(dep, city);
    }

    @GetMapping("/listar/fecha-creacion/{fi}/{ff}")
    public ResponseEntity<?> buscarPersonaFechaCreacion(@PathVariable("fi") Date fi, @PathVariable("ff") Date ff) throws Exception{
        return personaService.listarPersonasFechaCreacion(fi, ff);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearPersona(@RequestBody PersonaReq persona) throws Exception {
        return personaService.crearPersona(persona);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarPersona(@PathVariable("id") Long id,@RequestBody PersonaReq persona) throws Exception {
        return personaService.actualizarPersona(id,persona);
    }

}
