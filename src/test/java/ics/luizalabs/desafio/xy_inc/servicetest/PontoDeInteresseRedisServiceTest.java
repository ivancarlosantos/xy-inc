package ics.luizalabs.desafio.xy_inc.servicetest;

import ics.luizalabs.desafio.xy_inc.exceptions.RegraDeNegocioException;
import ics.luizalabs.desafio.xy_inc.model.PontoDeInteresseRedis;
import ics.luizalabs.desafio.xy_inc.repository.PontoDeInteresseRedisRepository;
import ics.luizalabs.desafio.xy_inc.service.PontoDeInteresseRedisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PontoDeInteresseRedisServiceTest {

    @Mock
    private PontoDeInteresseRedisRepository redisRepository;

    @InjectMocks
    private PontoDeInteresseRedisService redisService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testListen() {
        PontoDeInteresseRedis redis = new PontoDeInteresseRedis();
        redis.setLocalPoi("POI Test");

        redisService.listen(redis);

        verify(redisRepository, times(1)).save(redis);
    }

    @Test
    public void testPersistValidPOI() {
        PontoDeInteresseRedis redis = new PontoDeInteresseRedis();
        redis.setCoordX(10.0);
        redis.setCoordY(15.0);

        PontoDeInteresseRedis result = redisService.persist(redis);

        assertEquals(redis, result);
        verify(rabbitTemplate).convertAndSend(anyString(), anyString(), eq(redis));
    }

    @Test
    public void testPersistInvalidPOI() {

        PontoDeInteresseRedis redis = new PontoDeInteresseRedis();
        redis.setCoordX(-10.0);
        redis.setCoordY(15.0);

        assertThrows(RegraDeNegocioException.class, () -> redisService.persist(redis));
    }

    @Test
    void testPersistWithNegativeCoordinates() {
        PontoDeInteresseRedis redis = new PontoDeInteresseRedis("1L", "Lanchonete", -10.0, -20.0);

        assertThrows(RegraDeNegocioException.class, () -> redisService.persist(redis));
    }
}

