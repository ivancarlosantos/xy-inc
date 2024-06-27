package ics.luizalabs.desafio.xy_inc.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    private String localPoi;

    private Double coordX;

    private Double coordY;

    @CreationTimestamp
    private LocalDateTime criadoEm;
}
