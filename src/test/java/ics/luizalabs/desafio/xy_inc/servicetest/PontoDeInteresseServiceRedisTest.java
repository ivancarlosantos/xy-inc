package ics.luizalabs.desafio.xy_inc.servicetest;

import ics.luizalabs.desafio.xy_inc.dto.PontoDeInteresseDTO;
import ics.luizalabs.desafio.xy_inc.exceptions.RegraDeNegocioException;
import ics.luizalabs.desafio.xy_inc.model.PontoDeInteresseRedis;
import ics.luizalabs.desafio.xy_inc.repository.PontoDeInteresseRedisRepository;
import ics.luizalabs.desafio.xy_inc.service.PontoDeInteresseRedisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PontoDeInteresseServiceRedisTest {

    @InjectMocks
    private PontoDeInteresseRedisService redisService;

    @Mock
    private PontoDeInteresseRedisRepository redisRepository;

    @Test
    @DisplayName("Cadastra um Ponto De Interesse")
    void testPersist() {
        PontoDeInteresseDTO dto = new PontoDeInteresseDTO(1L, "Lanchonete", 27.0, 12.0);
        PontoDeInteresseRedis poiRedis = PontoDeInteresseRedis
                .builder()
                .id("1L")
                .localPoi(dto.localPoi())
                .coordX(dto.coordX())
                .coordY(dto.coordY())
                .build();

        when(redisRepository.save(any(PontoDeInteresseRedis.class))).thenReturn(poiRedis);

        PontoDeInteresseRedis result = redisService.persist(poiRedis);

        assertEquals(dto.localPoi(), result.getLocalPoi());
        assertEquals(dto.coordX(), result.getCoordX());
        assertEquals(dto.coordY(), result.getCoordY());

        assertThat(result.getLocalPoi()).isNotNull();
        assertThat(result.getCoordX()).isNotNull();
        assertThat(result.getCoordY()).isNotNull();

        verifyNoMoreInteractions(redisRepository);
    }

    @Test
    @DisplayName("Retorna Exceção Em Caso de Coordenada Negativa")
    void testPersistWithNegativeCoordinates() {
        PontoDeInteresseRedis redis = new PontoDeInteresseRedis("1L", "Lanchonete", -10.0, -20.0);

        assertThrows(RegraDeNegocioException.class, () -> redisService.persist(redis));
    }

    @Test
    @DisplayName("Retorna a Lista de Pontos de Interesse Cadastrados")
    void testList() {

        // Arrange
        PontoDeInteresseRedis redis1 = PontoDeInteresseRedis.builder()
                .id("1L")
                .localPoi("Lanchonete")
                .coordX(10.0)
                .coordY(20.0)
                .build();

        PontoDeInteresseRedis redis2 = PontoDeInteresseRedis.builder()
                .id("2L")
                .localPoi("Pub")
                .coordX(30.0)
                .coordY(40.0)
                .build();

        when(redisRepository.findAll()).thenReturn(Arrays.asList(redis1, redis2));

        // Act
        List<PontoDeInteresseRedis> result = redisService.list();

        // Assert
        assertEquals(2, result.size());
        verify(redisRepository, times(1)).findAll();

        assertThat(redis1.getLocalPoi()).isNotNull();
        assertThat(redis1.getCoordX()).isNotNull();
        assertThat(redis1.getCoordY()).isNotNull();

        assertThat(redis2.getLocalPoi()).isNotNull();
        assertThat(redis2.getCoordX()).isNotNull();
        assertThat(redis2.getCoordY()).isNotNull();

        verifyNoMoreInteractions(redisRepository);
    }

    @Test
    @DisplayName("Encontra um Local Cadastrado")
    void testFindLocalPOI() {
        // Arrange
        PontoDeInteresseRedis redis = PontoDeInteresseRedis
                .builder()
                .id("1L")
                .localPoi("Lanchonete")
                .coordX(10.0)
                .coordY(20.0)
                .build();

        when(redisRepository.findByLocalPOI("Lanchonete")).thenReturn(List.of(redis));

        // Act
        List<PontoDeInteresseRedis> result = redisService.findLocalPOI("Lanchonete");

        // Assert
        assertNotNull(result);
        assertEquals("Lanchonete", redis.getLocalPoi());
        assertEquals(10.0, redis.getCoordX());
        assertEquals(20.0, redis.getCoordY());

        verify(redisRepository, times(1)).findByLocalPOI("Lanchonete");

        verifyNoMoreInteractions(redisRepository);
    }

    @Test
    @DisplayName("Retorna uma Lista de Locais de Pontos de Interesse em relação a Distância Fornecida")
    void testSearchPOI() {
        //Arrange
        List<PontoDeInteresseRedis> redisList = List.of(
                new PontoDeInteresseRedis("1L", "Lanchonete", 10.0, 20.0),
                new PontoDeInteresseRedis("2L", "Posto", 15.0, 25.0)
        );

        when(redisRepository.findLocalRef(5.0, 25.0, 15.0, 35.0)).thenReturn(redisList);

        //Act
        List<PontoDeInteresseRedis> results = redisService.searchPOI(15.0, 25.0, 10.0);

        //Assert
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        assertEquals("Lanchonete", results.get(0).getLocalPoi());

        verifyNoMoreInteractions(redisRepository);
    }
}
