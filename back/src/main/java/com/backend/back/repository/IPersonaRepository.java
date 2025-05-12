package com.backend.back.repository;

import com.backend.back.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPersonaRepository extends JpaRepository<Persona, Long> {

    @Query("select p from Persona p where p.tipoDocumento = :td and p.documento = :doc")
    Optional<Persona> buscarPersonaPorDoc(@Param("td") String td, @Param("doc") String doc) throws Exception;
}
