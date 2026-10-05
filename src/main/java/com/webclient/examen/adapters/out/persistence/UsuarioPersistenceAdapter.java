package com.webclient.examen.adapters.out.persistence;

import com.webclient.examen.domain.model.Usuario;
import com.webclient.examen.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsuarioPersistenceAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository usuarioJpaRepository;

    public UsuarioPersistenceAdapter(UsuarioJpaRepository usuarioJpaRepository) {
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        UsuarioEntity entity = toEntity(usuario);
        UsuarioEntity savedEntity = usuarioJpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return usuarioJpaRepository.existsByCorreoIgnoreCase(correo);
    }

    @Override
    public boolean existePorUsuario(String usuario) {
        return usuarioJpaRepository.existsByUsuarioIgnoreCase(usuario);
    }

    @Override
    public List<Usuario> buscarPorCoincidencia(String texto) {
        return usuarioJpaRepository.buscarPorCoincidenciaParcial(texto)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioJpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioJpaRepository.findById(id).map(this::toDomain);
    }

    private UsuarioEntity toEntity(Usuario domain) {
        if (domain == null) return null;
        return new UsuarioEntity(
                domain.getId(),
                domain.getNombre(),
                domain.getApellidoPaterno(),
                domain.getApellidoMaterno(),
                domain.getCorreo(),
                domain.getUsuario(),
                domain.getPassword(),
                domain.getFechaNacimiento()
        );
    }

    private Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;
        return new Usuario(
                entity.getId(),
                entity.getNombre(),
                entity.getApellidoPaterno(),
                entity.getApellidoMaterno(),
                entity.getCorreo(),
                entity.getUsuario(),
                entity.getPassword(),
                entity.getFechaNacimiento()
        );
    }
}
