package ics.luizalabs.desafio.xy_inc.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ApiErrors {

    private List<String> errors;

    public ApiErrors(String messageErrors) {
        this.errors = List.of(messageErrors);
    }
}
