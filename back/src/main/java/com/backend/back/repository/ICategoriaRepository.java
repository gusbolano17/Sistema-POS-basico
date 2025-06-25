package com.backend.back.repository;

import com.backend.back.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria,Long> {

   @Query("select c from Categoria c where c.nombre like :nombre%")
   List<Categoria> listarCategoriaNombre(@Param("nombre") String nombre) throws Exception;

}
