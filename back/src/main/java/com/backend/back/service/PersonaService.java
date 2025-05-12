package com.backend.back.service;

import com.backend.back.models.Persona;
import com.backend.back.repository.IPersonaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private IPersonaRepository personaRepository;

    public List<Persona> listarPersonas() throws Exception {
        return personaRepository.findAll();
    }

    public Optional<Persona> buscarPersonaPorId(Long id) throws Exception {
        return personaRepository.findById(id);
    }

    Optional<Persona> buscarPersonaPorDoc(String tipoDocumento, String documento) throws Exception {
        return personaRepository.buscarPersonaPorDoc(tipoDocumento, documento);
    }

    @Transactional(rollbackOn = Exception.class)
    public Map<String,Object> crearPersona(Persona persona) throws Exception {
        Map<String,Object> response = new HashMap<>();

        try {
            Persona nuevaPersona = new Persona();
            nuevaPersona.setNombre(persona.getNombre());
            nuevaPersona.setApellido(persona.getApellido());
            nuevaPersona.setEmail(persona.getEmail());
            nuevaPersona.setTipoDocumento(persona.getTipoDocumento());
            nuevaPersona.setDocumento(persona.getDocumento());
            nuevaPersona.setDireccion(persona.getDireccion());
            nuevaPersona.setTelefono(persona.getTelefono());

            personaRepository.save(nuevaPersona);

            response.put("msg", "Persona guardada");
            response.put("code", 201);
            response.put("status", "created");
        }catch (Exception e){
            response.put("msg", e.getMessage());
            response.put("code", 500);
            response.put("status", "error");
        }

        return response;
    }

    @Transactional(rollbackOn = Exception.class)
    public Map<String,Object> actualizarPersona(Persona persona) throws Exception {
        Map<String,Object> response = new HashMap<>();
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

                personaRepository.save(personaExistente);
            });

        }catch (Exception e){
            response.put("msg", e.getMessage());
            response.put("code", 500);
            response.put("status", "error");
        }
        return response;
    }

}
