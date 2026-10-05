package com.webclient.examen.application;

import com.webclient.examen.application.services.UsuarioService;
import com.webclient.examen.domain.exception.UsuarioYaExisteException;
import com.webclient.examen.domain.exception.ValidacionNegocioException;
import com.webclient.examen.domain.model.Usuario;
import com.webclient.examen.domain.ports.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepositoryPort;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioValido;

    @BeforeEach
    void setUp() {
        usuarioValido = new Usuario(
                null,
                "Juan",
                "Pérez",
                "López",
                "juan.perez@example.com",
                "juanperez",
                "password123",
                LocalDate.of(1995, 5, 10)
        );
    }

    @Test
    void testRegistrarUsuarioExitoso() {
        when(usuarioRepositoryPort.existePorCorreo("juan.perez@example.com")).thenReturn(false);
        when(usuarioRepositoryPort.existePorUsuario("juanperez")).thenReturn(false);
        when(usuarioRepositoryPort.guardar(any(Usuario.class))).thenAnswer(i -> {
            Usuario u = i.getArgument(0);
            u.setId(1L);
            return u;
        });

        Usuario resultado = usuarioService.registrar(usuarioValido);

        assertNotNull(resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        verify(usuarioRepositoryPort, times(1)).guardar(any(Usuario.class));
    }

    @Test
    void testRegistrarUsuarioCorreoDuplicado() {
        when(usuarioRepositoryPort.existePorCorreo("juan.perez@example.com")).thenReturn(true);

        UsuarioYaExisteException ex = assertThrows(UsuarioYaExisteException.class, () -> {
            usuarioService.registrar(usuarioValido);
        });

        assertEquals("El correo electrónico ya está registrado.", ex.getMessage());
        verify(usuarioRepositoryPort, never()).guardar(any(Usuario.class));
    }

    @Test
    void testRegistrarUsuarioNombreUsuarioDuplicado() {
        when(usuarioRepositoryPort.existePorCorreo("juan.perez@example.com")).thenReturn(false);
        when(usuarioRepositoryPort.existePorUsuario("juanperez")).thenReturn(true);

        UsuarioYaExisteException ex = assertThrows(UsuarioYaExisteException.class, () -> {
            usuarioService.registrar(usuarioValido);
        });

        assertEquals("El nombre de usuario ya está registrado.", ex.getMessage());
        verify(usuarioRepositoryPort, never()).guardar(any(Usuario.class));
    }

    @Test
    void testRegistrarUsuarioMenosDe5Caracteres() {
        usuarioValido.setUsuario("juan"); // 4 caracteres

        ValidacionNegocioException ex = assertThrows(ValidacionNegocioException.class, () -> {
            usuarioService.registrar(usuarioValido);
        });

        assertEquals("El usuario deberá tener al menos 5 caracteres.", ex.getMessage());
    }

    @Test
    void testRegistrarPasswordMenosDe8Caracteres() {
        usuarioValido.setPassword("1234567"); // 7 caracteres

        ValidacionNegocioException ex = assertThrows(ValidacionNegocioException.class, () -> {
            usuarioService.registrar(usuarioValido);
        });

        assertEquals("La contraseña deberá tener al menos 8 caracteres.", ex.getMessage());
    }

    @Test
    void testBuscarUsuariosMenosDe3Caracteres() {
        ValidacionNegocioException ex1 = assertThrows(ValidacionNegocioException.class, () -> {
            usuarioService.buscarPorTexto("ju");
        });
        assertEquals("La búsqueda debe contener al menos 3 caracteres.", ex1.getMessage());

        ValidacionNegocioException ex2 = assertThrows(ValidacionNegocioException.class, () -> {
            usuarioService.buscarPorTexto("  a  ");
        });
        assertEquals("La búsqueda debe contener al menos 3 caracteres.", ex2.getMessage());

        verify(usuarioRepositoryPort, never()).buscarPorCoincidencia(anyString());
    }

    @Test
    void testBuscarUsuariosValido() {
        when(usuarioRepositoryPort.buscarPorCoincidencia("juan")).thenReturn(Collections.singletonList(usuarioValido));

        List<Usuario> resultados = usuarioService.buscarPorTexto("juan");

        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals("juanperez", resultados.get(0).getUsuario());
        verify(usuarioRepositoryPort, times(1)).buscarPorCoincidencia("juan");
    }
}
