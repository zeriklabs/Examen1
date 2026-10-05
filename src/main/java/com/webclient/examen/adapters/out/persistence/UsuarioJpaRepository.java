package com.webclient.examen.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {

    boolean existsByCorreoIgnoreCase(String correo);

    boolean existsByUsuarioIgnoreCase(String usuario);

    @Query("SELECT u FROM UsuarioEntity u WHERE " +
           "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
           "LOWER(u.apellidoPaterno) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
           "LOWER(u.apellidoMaterno) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
           "LOWER(u.usuario) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<UsuarioEntity> buscarPorCoincidenciaParcial(@Param("texto") String texto);
}
