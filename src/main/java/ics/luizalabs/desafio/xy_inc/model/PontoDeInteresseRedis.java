package ics.luizalabs.desafio.xy_inc.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "poi_redis")
public class PontoDeInteresseRedis {

    @Id
    @Indexed
    private String id;

    private String localPoi;

    private Double coordX;

    private Double coordY;

}
