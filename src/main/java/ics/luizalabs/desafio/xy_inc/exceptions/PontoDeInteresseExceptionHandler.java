package ics.luizalabs.desafio.xy_inc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class PontoDeInteresseExceptionHandler {

    @ExceptionHandler(RegraDeNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraDeNegocioException(RegraDeNegocioException ex) {

        String errors = ex.getMessage();
        return new ApiErrors(errors);
    }

    @ExceptionHandler(LocalNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleLocalNaoEncontradoException(LocalNaoEncontradoException ex) {

        String errors = ex.getMessage();
        return new ApiErrors(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        String errors = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        return new ApiErrors(errors);
    }
}
