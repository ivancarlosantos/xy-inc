package ics.luizalabs.desafio.xy_inc.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PontoDeInteresseDTO {

    private String localPoi;

    private Double coordX;

    private Double coordY;
}
