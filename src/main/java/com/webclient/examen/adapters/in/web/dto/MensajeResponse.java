package com.webclient.examen.adapters.in.web.dto;

import java.time.LocalDateTime;

public class MensajeResponse {
    private String mensaje;
    private int status;
    private LocalDateTime timestamp;

    public MensajeResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public MensajeResponse(String mensaje, int status) {
        this.mensaje = mensaje;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
