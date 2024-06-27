package ics.luizalabs.desafio.xy_inc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PontoDeInteresseDTO(
        @NotEmpty(message = "Campo Obrigatório")
        @NotBlank
        String localPoi,
        @NotNull(message = "Coordenada X Obrigatória")
        Double coordX,
        @NotNull(message = "Coordenada Y Obrigatória")
        Double coordY) {}
