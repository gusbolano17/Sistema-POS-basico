package com.backend.back.repository;

import com.backend.back.models.Municipios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICiudadRepository extends JpaRepository<Municipios, Long> {

    @Query("select m from Municipios m where m.departamentoId.nombre = :dep")
    public List<Municipios> listarPorDepartamento(@Param("dep") String dep) throws Exception;

}
