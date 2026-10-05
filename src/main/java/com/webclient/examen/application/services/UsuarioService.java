package com.webclient.examen.application.services;

import com.webclient.examen.domain.exception.UsuarioYaExisteException;
import com.webclient.examen.domain.exception.ValidacionNegocioException;
import com.webclient.examen.domain.model.Usuario;
import com.webclient.examen.domain.ports.in.BuscarUsuariosUseCase;
import com.webclient.examen.domain.ports.in.RegistrarUsuarioUseCase;
import com.webclient.examen.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UsuarioService implements RegistrarUsuarioUseCase, BuscarUsuariosUseCase {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public UsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    @Transactional
    public Usuario registrar(Usuario usuario) {
        validarCamposObligatorios(usuario);
        validarFormatoCorreo(usuario.getCorreo());
        validarLongitudUsuario(usuario.getUsuario());
        validarLongitudPassword(usuario.getPassword());
        validarFechaNacimiento(usuario.getFechaNacimiento());

        if (usuarioRepositoryPort.existePorCorreo(usuario.getCorreo().trim())) {
            throw new UsuarioYaExisteException("El correo electrónico ya está registrado.");
        }

        if (usuarioRepositoryPort.existePorUsuario(usuario.getUsuario().trim())) {
            throw new UsuarioYaExisteException("El nombre de usuario ya está registrado.");
        }

        return usuarioRepositoryPort.guardar(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorTexto(String texto) {
        if (texto == null || texto.trim().length() < 3) {
            throw new ValidacionNegocioException("La búsqueda debe contener al menos 3 caracteres.");
        }

        return usuarioRepositoryPort.buscarPorCoincidencia(texto.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodos() {
        return usuarioRepositoryPort.obtenerTodos();
    }

    private void validarCamposObligatorios(Usuario u) {
        if (u == null) {
            throw new ValidacionNegocioException("Los datos del usuario son obligatorios.");
        }
        if (estaVacio(u.getNombre())) {
            throw new ValidacionNegocioException("El nombre es obligatorio.");
        }
        if (estaVacio(u.getApellidoPaterno())) {
            throw new ValidacionNegocioException("El apellido paterno es obligatorio.");
        }
        if (estaVacio(u.getApellidoMaterno())) {
            throw new ValidacionNegocioException("El apellido materno es obligatorio.");
        }
        if (estaVacio(u.getCorreo())) {
            throw new ValidacionNegocioException("El correo electrónico es obligatorio.");
        }
        if (estaVacio(u.getUsuario())) {
            throw new ValidacionNegocioException("El nombre de usuario es obligatorio.");
        }
        if (estaVacio(u.getPassword())) {
            throw new ValidacionNegocioException("La contraseña es obligatoria.");
        }
        if (u.getFechaNacimiento() == null) {
            throw new ValidacionNegocioException("La fecha de nacimiento es obligatoria.");
        }
    }

    private void validarFormatoCorreo(String correo) {
        if (!EMAIL_PATTERN.matcher(correo.trim()).matches()) {
            throw new ValidacionNegocioException("El correo electrónico no tiene un formato válido.");
        }
    }

    private void validarLongitudUsuario(String usuario) {
        if (usuario.trim().length() < 5) {
            throw new ValidacionNegocioException("El usuario deberá tener al menos 5 caracteres.");
        }
    }

    private void validarLongitudPassword(String password) {
        if (password.length() < 8) {
            throw new ValidacionNegocioException("La contraseña deberá tener al menos 8 caracteres.");
        }
    }

    private void validarFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new ValidacionNegocioException("La fecha de nacimiento no puede ser una fecha futura.");
        }
    }

    private boolean estaVacio(String str) {
        return str == null || str.trim().isEmpty();
    }
}
