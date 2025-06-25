package com.backend.back.service;

import com.backend.back.models.Categoria;
import com.backend.back.models.dtos.ResponseDTO;
import com.backend.back.repository.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    public ResponseEntity<ResponseDTO<List<Categoria>>> listarCategorias() throws Exception{
        try {
            return ResponseEntity.ok(
                    new ResponseDTO<>(
                            "Categorias encontradas",
                            "ok",
                            categoriaRepository.findAll()
                    )
            );
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(ex.getMessage(),"error", null));
        }
    }

    public ResponseEntity<ResponseDTO<List<Categoria>>> listarCategoriasNombre(String nombre) throws Exception{
        try {

            var categorias = categoriaRepository.listarCategoriaNombre(nombre);
            if(categorias.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>("Categoria no encontrada", "error", null));
            }

            return ResponseEntity.ok(new ResponseDTO<>("Categoria encontrada", "ok", categorias));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(),"error", null));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseDTO<Categoria>> agregarCategoria(Map<String,Object> catbody) throws Exception{
        try {

            Optional<Categoria> categoriaExist =
                    categoriaRepository.listarCategoriaNombre(catbody.get("nombre").toString()).stream()
                            .findAny();

            if (categoriaExist.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO<>("Categoria existente", "error", null));
            }

            Categoria categoria = new Categoria();
            categoria.setNombre(catbody.get("nombre").toString());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO<>("Categoria agregada", "ok", categoriaRepository.save(categoria)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(),"error", null));
        }
    }

}
