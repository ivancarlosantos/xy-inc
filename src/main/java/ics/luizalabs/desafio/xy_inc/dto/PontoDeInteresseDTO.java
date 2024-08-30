package ics.luizalabs.desafio.xy_inc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PontoDeInteresseDTO(
        Long id,
        @NotNull(message = "Coordenada Y não dever vazio e/ou nulo")
        @NotEmpty(message = "Campo 'LOCAL' não deve ser vazio e/ou nulo")
        @NotBlank(message = "Campo 'LOCAL' não deve ser vazio e/ou nulo")
        String localPoi,
        @NotNull(message = "Coordenada X não dever vazio e/ou nulo")
        Double coordX,
        @NotNull(message = "Coordenada Y não dever vazio e/ou nulo")
        Double coordY) {}
