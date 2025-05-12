package com.backend.back.repository;

import com.backend.back.models.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IInventarioRepository extends JpaRepository<Inventario, Long> {

    @Query("select i from Inventario i where i.nombre = :nombre")
    public Optional<Inventario> obtenerPorNombre(@Param("nombre") String nombre);


}
