package ics.luizalabs.desafio.xy_inc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

@ControllerAdvice
public class POIExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ExceptionMessage> handlerExceptionBadRequest(RuntimeException ex) {

        ExceptionMessage exceptionMensagem = new ExceptionMessage(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                new Date(),
                ex.getMessage());

        return new ResponseEntity<>(exceptionMensagem, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ExceptionMessage> handlerExceptionFormatNumber(MethodArgumentTypeMismatchException ex) {

        ExceptionMessage exceptionMessage = new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                new Date(),
                ex.getMessage());

        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }
}
