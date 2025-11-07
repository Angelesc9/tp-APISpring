package com.utn.productos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductoNotFoundException.class) // Le decimos qué excepción específica maneja
    public ResponseEntity<ErrorResponse> manejarProductoNoEncontrado(ProductoNotFoundException error, WebRequest request) {

        ErrorResponse respuesta = new ErrorResponse();
        respuesta.setTimestamp(new Date());
        respuesta.setHttpStatus(HttpStatus.NOT_FOUND.value()); // Esto es 404
        respuesta.setErrorMessage(error.getMessage());


        respuesta.setRoute(request.getDescription(false));

        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> datosNoValidos(MethodArgumentNotValidException error, WebRequest request) {
        ErrorResponse respuesta = new ErrorResponse();
        respuesta.setTimestamp(new Date());
        respuesta.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        respuesta.setErrorMessage("Error de validación: " + error.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        respuesta.setRoute(request.getDescription(false));
        
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarExcepcionGeneral(Exception error, WebRequest request) {
        ErrorResponse respuesta = new ErrorResponse();
        respuesta.setTimestamp(new Date());
        respuesta.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        respuesta.setErrorMessage("Error interno del servidor: " + error.getMessage());
        respuesta.setRoute(request.getDescription(false));
        
        return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
