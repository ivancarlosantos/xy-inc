package ics.luizalabs.desafio.xy_inc.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_poi")
public class PontoDeInteresseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomePoi;

    private Integer coordX;

    private Integer coordY;

    private LocalDateTime createAt;
}
