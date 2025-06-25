package com.backend.back.service;

import com.backend.back.models.Inventario;
import com.backend.back.models.Persona;
import com.backend.back.models.dtos.InventarioReq;
import com.backend.back.models.dtos.ResponseDTO;
import com.backend.back.repository.IInventarioRepository;
import com.backend.back.repository.IPersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private IInventarioRepository inventarioRepository;
    @Autowired
    private IPersonaRepository personaRepository;

    public List<Inventario> listarInventarios() throws Exception {
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> buscarInventarioPorId(Long id) throws Exception {
        return inventarioRepository.findById(id);
    }

    Optional<Inventario> obtenerPorNombre(String nombre) throws Exception {
        return inventarioRepository.obtenerPorNombre(nombre);
    }

    public ResponseEntity<ResponseDTO<List<Inventario>>> listarInventarioCategoria(String categoria) throws Exception {
        try {
            var listaInventario = inventarioRepository.listarPorCategoria(categoria);
            if(listaInventario.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseDTO<>("No se encontraron resultados", "error", null)
                );
            }
            return ResponseEntity.ok(new ResponseDTO<>("Inventario encontrado", "ok", listaInventario));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseDTO<>(e.getMessage(), "error", null)
            );
        }
    }

    public ResponseEntity<ResponseDTO<List<Inventario>>> listarInventarioProveedor(String prov) throws Exception {
        try {
            var listaInventario = inventarioRepository.listarPorProveedor(prov);
            if(listaInventario.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseDTO<>("No se encontraron resultados", "error", null)
                );
            }
            return ResponseEntity.ok(new ResponseDTO<>("Inventario encontrado", "ok", listaInventario));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseDTO<>(e.getMessage(), "error", null)
            );
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseDTO<Inventario>> crearInventario(InventarioReq inventReq) throws Exception {

        try {

            Optional<Persona> proveedor = personaRepository
                    .buscarPersonaPorDoc(inventReq.proveedor().get("tipoDocumento"),inventReq.proveedor().get("documento"));

            if (proveedor.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO<>(
                                "El proveedor no existe",
                                "error",
                                null
                                )
                        );
            }

            Inventario inventario = new Inventario();
            inventario.setNombre(inventReq.nombre());
            inventario.setDescripcion(inventReq.descripcion());
            inventario.setProveedorId(proveedor.get());
            inventario.setCategoriaId(inventReq.categoria());
            inventario.setStock(inventReq.stock());
            inventario.setPrecio(inventReq.precio());

            inventarioRepository.save(inventario);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO<>(
                            "El inventario ha sido agregado",
                            "ok" ,
                            inventario)
                    );


        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(ex.getMessage(), "error" ,null));
        }

    }

    @Transactional(rollbackFor = Exception.class)
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
