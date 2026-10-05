package com.webclient.examen.domain.ports.in;

import com.webclient.examen.domain.model.Usuario;
import java.util.List;

public interface BuscarUsuariosUseCase {
    List<Usuario> buscarPorTexto(String texto);
    List<Usuario> obtenerTodos();
}
