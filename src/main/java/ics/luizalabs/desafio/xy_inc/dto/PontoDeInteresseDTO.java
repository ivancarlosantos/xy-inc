package ics.luizalabs.desafio.xy_inc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PontoDeInteresseDTO(
        @NotEmpty
        @NotBlank
        String localPoi,
        @NotNull
        Double coordX,
        @NotNull
        Double coordY) {}
