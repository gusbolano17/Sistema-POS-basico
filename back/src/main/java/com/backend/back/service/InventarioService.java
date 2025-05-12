package com.backend.back.service;

import com.backend.back.models.Inventario;
import com.backend.back.repository.IInventarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private IInventarioRepository inventarioRepository;

    public List<Inventario> listarInventarios() throws Exception {
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> buscarInventarioPorId(Long id) throws Exception {
        return inventarioRepository.findById(id);
    }

    Optional<Inventario> obtenerPorNombre(String nombre) throws Exception {
        return inventarioRepository.obtenerPorNombre(nombre);
    }

    public Map<String,Object> crearInventario(Inventario inventario) throws Exception {
        Map<String,Object> response = new HashMap<>();
        try {

            Inventario nuevoInventario = new Inventario();
            nuevoInventario.setNombre(inventario.getNombre());
            nuevoInventario.setDescripcion(inventario.getDescripcion());
            nuevoInventario.setPrecio(inventario.getPrecio());
            nuevoInventario.setStock(inventario.getStock());
            nuevoInventario.setCategoriaId(inventario.getCategoriaId());
            nuevoInventario.setProveedorId(inventario.getProveedorId());

            inventarioRepository.save(nuevoInventario);

        }catch (Exception e){
            response.put("msg", e.getMessage());
            response.put("code", 500);
            response.put("status", "error");
        }

        return response;

    }

    @Transactional(rollbackOn = Exception.class)
    public Map<String,Object> actualizarInventario(Inventario inventario) throws Exception {
        Map<String,Object> response = new HashMap<>();
        try{

            Optional<Inventario> inventarioExist = inventarioRepository.findById(inventario.getId());
            inventarioExist.ifPresent(inventarioExistente -> {
                inventarioExistente.setNombre(inventario.getNombre());
                inventarioExistente.setDescripcion(inventario.getDescripcion());
                inventarioExistente.setPrecio(inventario.getPrecio());
                inventarioExistente.setStock(inventario.getStock());
                inventarioExistente.setCategoriaId(inventario.getCategoriaId());
                inventarioExistente.setProveedorId(inventario.getProveedorId());

                inventarioRepository.save(inventarioExistente);
            });

            response.put("msg", "Inventario actualizado");
            response.put("code", 201);
            response.put("status", "created");

        }catch (Exception e){
            response.put("msg", e.getMessage());
            response.put("code", 500);
            response.put("status", "error");
        }

        return response;
    }

}
