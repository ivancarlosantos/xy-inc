package ics.luizalabs.desafio.xy_inc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> handleArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> error = new LinkedHashMap<>();
        errors.forEach(er -> {
            String message = er.getDefaultMessage();
            String field = ((FieldError) (er)).getField();
            error.put(field, message);
        });
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}