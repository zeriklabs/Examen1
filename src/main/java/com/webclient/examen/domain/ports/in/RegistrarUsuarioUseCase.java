package com.webclient.examen.domain.ports.in;

import com.webclient.examen.domain.model.Usuario;

public interface RegistrarUsuarioUseCase {
    Usuario registrar(Usuario usuario);
}
