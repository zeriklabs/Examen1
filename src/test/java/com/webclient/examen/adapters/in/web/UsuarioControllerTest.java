package com.webclient.examen.adapters.in.web;

import com.webclient.examen.domain.exception.ValidacionNegocioException;
import com.webclient.examen.domain.model.Usuario;
import com.webclient.examen.domain.ports.in.BuscarUsuariosUseCase;
import com.webclient.examen.domain.ports.in.RegistrarUsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegistrarUsuarioUseCase registrarUsuarioUseCase;

    @Mock
    private BuscarUsuariosUseCase buscarUsuariosUseCase;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testRegistrarUsuarioHttp201() throws Exception {
        String jsonPayload = """
                {
                    "nombre": "Juan",
                    "apellidoPaterno": "Pérez",
                    "apellidoMaterno": "López",
                    "correo": "juan@example.com",
                    "usuario": "juanperez",
                    "password": "password123",
                    "fechaNacimiento": "1995-05-10"
                }
                """;

        Usuario usuarioCreado = new Usuario(1L, "Juan", "Pérez", "López", "juan@example.com", "juanperez", "password123", LocalDate.of(1995, 5, 10));

        when(registrarUsuarioUseCase.registrar(any(Usuario.class))).thenReturn(usuarioCreado);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellidoPaterno").value("Pérez"))
                .andExpect(jsonPath("$.apellidoMaterno").value("López"))
                .andExpect(jsonPath("$.usuario").value("juanperez"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void testBuscarUsuariosMenosDe3CaracteresHttp400() throws Exception {
        when(buscarUsuariosUseCase.buscarPorTexto("ju"))
                .thenThrow(new ValidacionNegocioException("La búsqueda debe contener al menos 3 caracteres."));

        mockMvc.perform(get("/api/v1/usuarios/buscar")
                        .param("texto", "ju"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("La búsqueda debe contener al menos 3 caracteres."));
    }

    @Test
    void testBuscarUsuariosExitoso() throws Exception {
        Usuario usuario = new Usuario(1L, "Juan", "Pérez", "López", "juan@example.com", "juanperez", "password123", LocalDate.of(1995, 5, 10));

        when(buscarUsuariosUseCase.buscarPorTexto("juan")).thenReturn(Collections.singletonList(usuario));

        mockMvc.perform(get("/api/v1/usuarios/buscar")
                        .param("texto", "juan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].usuario").value("juanperez"))
                .andExpect(jsonPath("$[0].password").doesNotExist());
    }
}
