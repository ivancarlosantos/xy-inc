package ics.luizalabs.desafio.xy_inc.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PontoDeInteresseDTO {

    private String nomePoi;

    private Integer coordX;

    private Integer coordY;
}
