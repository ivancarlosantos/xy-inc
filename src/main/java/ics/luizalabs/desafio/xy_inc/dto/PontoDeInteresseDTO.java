package ics.luizalabs.desafio.xy_inc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PontoDeInteresseDTO {

    @NotEmpty(message = "CAMPO LOCAL NÃO PODE SER VAZIO E/OU NULO")
    @NotBlank(message = "CAMPO LOCAL NÃO PODE SER VAZIO E/OU NULO")
    private String localPoi;

    @NotNull(message = "COORDENADA X NÃO PODE SER VAZIO E/OU NULO")
    private Double coordX;

    @NotNull(message = "COORDENADA Y NÃO PODE SER VAZIO E/OU NULO")
    private Double coordY;
}
