package com.backend.back.service;

import com.backend.back.models.Persona;
import com.backend.back.models.dtos.PersonaReq;
import com.backend.back.models.dtos.ResponseDTO;
import com.backend.back.repository.IPersonaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonaService {

    @Autowired
    private IPersonaRepository personaRepository;


    public ResponseEntity<ResponseDTO<List<Persona>>> listarPersonas() throws Exception {
        try {
            var listaPersonas = personaRepository.findAll();

            if (listaPersonas.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>("No hay personas registradas", "error", null));
            }else{
                return ResponseEntity.ok(new ResponseDTO<>("Personas encontradas", "ok", listaPersonas));
            }

        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));
        }
    }

    public Optional<Persona> buscarPersonaPorId(Long id) throws Exception {
        return personaRepository.findById(id);
    }

    public ResponseEntity<ResponseDTO<Persona>> buscarPersonaPorDoc(String tipoDocumento, String documento) throws Exception {
        try{
            Optional<Persona> persona = personaRepository.buscarPersonaPorDoc(tipoDocumento, documento);
            return persona
                    .map(value ->
                            ResponseEntity.ok(new ResponseDTO<>("Persona encontrada", "ok", value)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO<>("Persona no encontrada", "error", null)));
        }catch (DataAccessException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ResponseDTO<Persona>> crearPersona(PersonaReq persona) throws Exception {

        try {
            Persona nuevaPersona = new Persona();
            nuevaPersona.setNombre(persona.nombre());
            nuevaPersona.setApellido(persona.apellido());
            nuevaPersona.setEmail(persona.email());
            nuevaPersona.setTipoDocumento(persona.tipoDocumento());
            nuevaPersona.setDocumento(persona.documento());
            nuevaPersona.setDireccion(persona.direccion());
            nuevaPersona.setTelefono(persona.telefono());
            nuevaPersona.setFechaNacimiento(persona.fechaNacimiento());
            nuevaPersona.setPais(persona.pais());
            nuevaPersona.setDepartamentoId(persona.departamento());
            nuevaPersona.setCiudadId(persona.ciudad());

            personaRepository.save(nuevaPersona);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO<>("Persona guardada", "ok", nuevaPersona));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));
        }

    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ResponseDTO<Optional<Persona>>> actualizarPersona(Persona persona) throws Exception {

        try{

            Optional<Persona> personaExist = buscarPersonaPorId(persona.getId());
            personaExist.ifPresent(personaExistente -> {
                personaExistente.setNombre(persona.getNombre());
                personaExistente.setApellido(persona.getApellido());
                personaExistente.setEmail(persona.getEmail());
                personaExistente.setTipoDocumento(persona.getTipoDocumento());
                personaExistente.setDocumento(persona.getDocumento());
                personaExistente.setDireccion(persona.getDireccion());
                personaExistente.setTelefono(persona.getTelefono());
                personaExistente.setFechaNacimiento(persona.getFechaNacimiento());
                personaExistente.setPais(persona.getPais());
                personaExistente.setDepartamentoId(persona.getDepartamentoId());
                personaExistente.setCiudadId(persona.getCiudadId());

                personaRepository.save(personaExistente);

            });

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO<>("Persona actualizada", "ok", personaExist));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));

        }


    }

}
