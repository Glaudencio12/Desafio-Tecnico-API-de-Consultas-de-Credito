package com.glaudencio12.Consulta_de_Creditos.exceptions.handler;

import com.glaudencio12.Consulta_de_Creditos.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.NotActiveException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class CustomEntityResponseHandler {

    LocalDateTime localDateTime = LocalDateTime.now();
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    String dataFomatada = formato.format(localDateTime);

    @ExceptionHandler(NotActiveException.class)
    public final ResponseEntity<ExceptionResponse> handlerNotFoundException(Exception ex, WebRequest request){
        ExceptionResponse response = new ExceptionResponse(dataFomatada, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
