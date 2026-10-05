package com.webclient.examen.config;

import com.webclient.examen.domain.model.Usuario;
import com.webclient.examen.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return args -> {
            if (!usuarioRepositoryPort.existePorUsuario("juanperez")) {
                usuarioRepositoryPort.guardar(new Usuario(
                        null,
                        "Juan",
                        "Pérez",
                        "López",
                        "juan.perez@example.com",
                        "juanperez",
                        "password123",
                        LocalDate.of(1995, 4, 12)
                ));
            }

            if (!usuarioRepositoryPort.existePorUsuario("juancarlos")) {
                usuarioRepositoryPort.guardar(new Usuario(
                        null,
                        "Juan Carlos",
                        "Hernández",
                        "Gómez",
                        "jc.hernandez@example.com",
                        "juancarlos",
                        "password123",
                        LocalDate.of(1998, 8, 23)
                ));
            }

            if (!usuarioRepositoryPort.existePorUsuario("juanr")) {
                usuarioRepositoryPort.guardar(new Usuario(
                        null,
                        "Juan",
                        "Rodríguez",
                        "Sánchez",
                        "juan.rodriguez@example.com",
                        "juanr",
                        "password123",
                        LocalDate.of(2000, 11, 5)
                ));
            }

            if (!usuarioRepositoryPort.existePorUsuario("carlosr")) {
                usuarioRepositoryPort.guardar(new Usuario(
                        null,
                        "Carlos",
                        "Ramírez",
                        "Torres",
                        "carlos.ramirez@example.com",
                        "carlosr",
                        "password123",
                        LocalDate.of(1997, 2, 18)
                ));
            }
        };
    }
}
