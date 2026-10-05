package com.webclient.examen.adapters.in.web;

import com.webclient.examen.adapters.in.web.dto.UsuarioRegistroRequest;
import com.webclient.examen.adapters.in.web.dto.UsuarioResponse;
import com.webclient.examen.domain.model.Usuario;
import com.webclient.examen.domain.ports.in.BuscarUsuariosUseCase;
import com.webclient.examen.domain.ports.in.RegistrarUsuarioUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final BuscarUsuariosUseCase buscarUsuariosUseCase;

    public UsuarioController(RegistrarUsuarioUseCase registrarUsuarioUseCase,
                             BuscarUsuariosUseCase buscarUsuariosUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
        this.buscarUsuariosUseCase = buscarUsuariosUseCase;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> registrarUsuario(@Valid @RequestBody UsuarioRegistroRequest request) {
        Usuario nuevoUsuario = new Usuario(
                null,
                request.getNombre(),
                request.getApellidoPaterno(),
                request.getApellidoMaterno(),
                request.getCorreo(),
                request.getUsuario(),
                request.getPassword(),
                request.getFechaNacimiento()
        );

        Usuario registrado = registrarUsuarioUseCase.registrar(nuevoUsuario);

        UsuarioResponse response = new UsuarioResponse(
                registrado.getId(),
                registrado.getNombre(),
                registrado.getApellidoPaterno(),
                registrado.getApellidoMaterno(),
                registrado.getUsuario()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioResponse>> buscarUsuarios(@RequestParam(name = "texto", required = false) String texto) {
        List<Usuario> resultados = buscarUsuariosUseCase.buscarPorTexto(texto);

        List<UsuarioResponse> responseList = resultados.stream()
                .map(u -> new UsuarioResponse(
                        u.getId(),
                        u.getNombre(),
                        u.getApellidoPaterno(),
                        u.getApellidoMaterno(),
                        u.getUsuario()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }
}
