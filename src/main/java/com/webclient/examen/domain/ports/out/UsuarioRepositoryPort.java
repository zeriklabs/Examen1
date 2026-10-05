package com.webclient.examen.domain.ports.out;

import com.webclient.examen.domain.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    Usuario guardar(Usuario usuario);
    boolean existePorCorreo(String correo);
    boolean existePorUsuario(String usuario);
    List<Usuario> buscarPorCoincidencia(String texto);
    List<Usuario> obtenerTodos();
    Optional<Usuario> buscarPorId(Long id);
}
