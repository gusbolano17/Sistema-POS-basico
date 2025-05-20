package com.backend.back.repository;

import com.backend.back.models.Departamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDepartamentoRepository extends JpaRepository<Departamentos, Long> {

    @Query("select d from Departamentos d where d.nombre = :dep")
    Optional<Departamentos> obtenerPorNombre(@Param("id") String dep);
}
