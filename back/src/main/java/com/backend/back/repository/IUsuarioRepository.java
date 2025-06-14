package com.backend.back.repository;

import com.backend.back.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u where u.username like :name%")
    List<Usuario> listarUsuariosNombre(@Param("name") String name) throws Exception;

    @Query("select u from Usuario u where u.fechaCreacion between :fi and :ff")
    List<Usuario> listarUsuariosFecha(@Param("fi")Date fi, @Param("ff")Date ff) throws Exception;

    Optional<Usuario> findByUsername(String username);

}
