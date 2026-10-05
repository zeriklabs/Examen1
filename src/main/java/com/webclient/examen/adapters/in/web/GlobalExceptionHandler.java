package com.webclient.examen.adapters.in.web;

import com.webclient.examen.adapters.in.web.dto.MensajeResponse;
import com.webclient.examen.domain.exception.UsuarioYaExisteException;
import com.webclient.examen.domain.exception.ValidacionNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidacionNegocioException.class)
    public ResponseEntity<MensajeResponse> handleValidacionNegocio(ValidacionNegocioException ex) {
        MensajeResponse error = new MensajeResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UsuarioYaExisteException.class)
    public ResponseEntity<MensajeResponse> handleUsuarioYaExiste(UsuarioYaExisteException ex) {
        MensajeResponse error = new MensajeResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensajeResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        String mensaje = "Error en los datos enviados.";
        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError != null && fieldError.getDefaultMessage() != null) {
            mensaje = fieldError.getDefaultMessage();
        }
        MensajeResponse error = new MensajeResponse(mensaje, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MensajeResponse> handleIllegalArgument(IllegalArgumentException ex) {
        MensajeResponse error = new MensajeResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensajeResponse> handleGeneralException(Exception ex) {
        MensajeResponse error = new MensajeResponse("Ha ocurrido un error inesperado en el servidor: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
