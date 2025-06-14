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

    public ResponseEntity<ResponseDTO<List<Persona>>> buscarPersonasPorNombre(String nombre) throws Exception {
        try{
            List<Persona> personas = personaRepository.buscarPersonaPorNombre(nombre);
            if (personas.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>("No se encontraron personas con ese nombre", "error", null));
            }else{
                return ResponseEntity.ok(new ResponseDTO<>("Personas encontradas", "ok", personas));
            }
        }catch (DataAccessException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));
        }
    }

    public ResponseEntity<ResponseDTO<List<Persona>>> buscarPersonaLocacion(String departamento, String ciudad) throws Exception {
        try{
            List<Persona> personas = personaRepository.buscarPersonaLocacion(departamento, ciudad);
            if (personas.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>("No se encontraron personas con ese nombre", "error", null));
            }else{
                return ResponseEntity.ok(new ResponseDTO<>("Personas encontradas", "ok", personas));
            }
        }catch (DataAccessException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));
        }
    }

    public ResponseEntity<ResponseDTO<List<Persona>>> listarPersonasFechaCreacion(Date fi, Date ff) throws Exception {
        try{
            List<Persona> personas = personaRepository.listarPersonasFechaCreacion(fi, ff);
            if (personas.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>("No se encontraron personas con ese nombre", "error", null));
            }else{
                return ResponseEntity.ok(new ResponseDTO<>("Personas encontradas", "ok", personas));
            }
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
            nuevaPersona.setFechaCreacion(new Date());
            nuevaPersona.setFechaModificacion(new Date());

            personaRepository.save(nuevaPersona);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO<>("Persona guardada", "ok", nuevaPersona));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));
        }

    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ResponseDTO<Optional<Persona>>> actualizarPersona(Long id ,PersonaReq persona) throws Exception {

        try{

            Optional<Persona> personaExist = buscarPersonaPorId(id);
            personaExist.ifPresent(personaExistente -> {
                personaExistente.setNombre(persona.nombre());
                personaExistente.setApellido(persona.apellido());
                personaExistente.setEmail(persona.email());
                personaExistente.setTipoDocumento(persona.tipoDocumento());
                personaExistente.setDocumento(persona.documento());
                personaExistente.setDireccion(persona.direccion());
                personaExistente.setTelefono(persona.telefono());
                personaExistente.setFechaNacimiento(persona.fechaNacimiento());
                personaExistente.setPais(persona.pais());
                personaExistente.setDepartamentoId(persona.departamento());
                personaExistente.setCiudadId(persona.ciudad());
                personaExistente.setFechaModificacion(new Date());

                personaRepository.save(personaExistente);

            });

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO<>("Persona actualizada", "ok", personaExist));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));

        }


    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ResponseDTO<?>> eliminarPersona(Long id) throws Exception {

        try {

            Optional<Persona> personaExist = buscarPersonaPorId(id);

            if(personaExist.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>("Persona no encontrada", "error", null));
            }

            this.personaRepository.delete(personaExist.get());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO<>("Persona eliminada", "ok", null));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));
        }

    }

}
