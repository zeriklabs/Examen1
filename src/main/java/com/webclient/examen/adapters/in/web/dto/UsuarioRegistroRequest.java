package com.webclient.examen.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UsuarioRegistroRequest {

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio.")
    @Size(max = 50, message = "El apellido paterno no puede exceder 50 caracteres.")
    @JsonAlias({"apellido_paterno", "apellidoPaterno"})
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno es obligatorio.")
    @Size(max = 50, message = "El apellido materno no puede exceder 50 caracteres.")
    @JsonAlias({"apellido_materno", "apellidoMaterno"})
    private String apellidoMaterno;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico no tiene un formato válido.")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres.")
    private String correo;

    @NotBlank(message = "El nombre de usuario es obligatorio.")
    @Size(min = 5, max = 50, message = "El usuario deberá tener al menos 5 caracteres.")
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 8, max = 100, message = "La contraseña deberá tener al menos 8 caracteres.")
    private String password;

    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonAlias({"fecha_nacimiento", "fechaNacimiento"})
    private LocalDate fechaNacimiento;

    public UsuarioRegistroRequest() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
