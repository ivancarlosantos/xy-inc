package ics.luizalabs.desafio.xy_inc.servicetest;

import ics.luizalabs.desafio.xy_inc.dto.PontoDeInteresseDTO;
import ics.luizalabs.desafio.xy_inc.exceptions.LocalNaoEncontradoException;
import ics.luizalabs.desafio.xy_inc.exceptions.RegraDeNegocioException;
import ics.luizalabs.desafio.xy_inc.model.PontoDeInteresseModel;
import ics.luizalabs.desafio.xy_inc.repository.PontoDeInteresseRepository;
import ics.luizalabs.desafio.xy_inc.service.PontoDeInteresseService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PontoDeInteresseServiceTest {

    @InjectMocks
    private PontoDeInteresseService service;

    @Mock
    private PontoDeInteresseRepository repository;

    @Test
    @DisplayName("Cadastra um Ponto De Interesse")
    void testPersist() {
        PontoDeInteresseDTO dto = new PontoDeInteresseDTO("Lanchonete", 10.0, 20.0);
        PontoDeInteresseModel model = PontoDeInteresseModel
                .builder()
                .localPoi("Lanchonete")
                .coordX(27.0)
                .coordY(12.0)
                .build();

        when(repository.save(any(PontoDeInteresseModel.class))).thenReturn(model);

        PontoDeInteresseDTO result = service.persist(dto);

        assertEquals(dto.localPoi(), result.localPoi());
        assertEquals(dto.coordX(), result.coordX());
        assertEquals(dto.coordY(), result.coordY());

        Assertions.assertThat(result.localPoi()).isNotNull();
        Assertions.assertThat(result.coordX()).isNotNull();
        Assertions.assertThat(result.coordY()).isNotNull();

        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Retorna Exceção Em Caso de Coordenada Negativa")
    void testPersistWithNegativeCoordinates() {
        PontoDeInteresseDTO dto = new PontoDeInteresseDTO("Lanchonete", -10.0, -20.0);

        assertThrows(RegraDeNegocioException.class, () -> service.persist(dto));
    }

    @Test
    @DisplayName("Retorna Exceção Em Caso De Local Não Encontrado")
    void testFindLocalPOINotFound() {
        when(repository.findByLocalPOI("Lanchonete")).thenReturn(Optional.empty());

        assertThrows(LocalNaoEncontradoException.class, () -> service.findLocalPOI("Lanchonete"));

        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Retorna a Lista de Pontos de Interesse Cadastrados")
    void testList() {

        // Arrange
        PontoDeInteresseModel model1 = PontoDeInteresseModel.builder()
                .localPoi("Lanchonete")
                .coordX(10.0)
                .coordY(20.0)
                .criadoEm(LocalDateTime.now())
                .build();

        PontoDeInteresseModel model2 = PontoDeInteresseModel
                .builder()
                .localPoi("Pub")
                .coordX(30.0)
                .coordY(40.0)
                .build();

        when(repository.findAll()).thenReturn(Arrays.asList(model1, model2));

        // Act
        List<PontoDeInteresseDTO> result = service.list();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(new PontoDeInteresseDTO("Lanchonete", 10.0, 20.0)));
        assertTrue(result.contains(new PontoDeInteresseDTO("Pub", 30.0, 40.0)));
        verify(repository, times(1)).findAll();

        Assertions.assertThat(model1.getLocalPoi()).isNotNull();
        Assertions.assertThat(model1.getCoordX()).isNotNull();
        Assertions.assertThat(model1.getCoordY()).isNotNull();

        Assertions.assertThat(model2.getLocalPoi()).isNotNull();
        Assertions.assertThat(model2.getCoordX()).isNotNull();
        Assertions.assertThat(model2.getCoordY()).isNotNull();

        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Encontra um Local Cadastrado")
    void testFindLocalPOI() {
        // Arrange
        PontoDeInteresseModel model = PontoDeInteresseModel
                .builder()
                .localPoi("Lanchonete")
                .coordX(10.0)
                .coordY(20.0)
                .build();

        when(repository.findByLocalPOI("Lanchonete")).thenReturn(Optional.of(model));

        // Act
        PontoDeInteresseDTO result = service.findLocalPOI("Lanchonete");

        // Assert
        assertNotNull(result);
        assertEquals("Lanchonete", result.localPoi());
        assertEquals(10.0, result.coordX());
        assertEquals(20.0, result.coordY());
        verify(repository, times(1)).findByLocalPOI("Lanchonete");

        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Retorna uma Lista de Locais de Pontos de Interesse em relação a Distância Fornecida")
    void testSearchPOI() {
        //Arrange
        List<PontoDeInteresseModel> models = List.of(
                new PontoDeInteresseModel(1L, "Lanchonete", 10.0, 20.0, LocalDateTime.now()),
                new PontoDeInteresseModel(2L, "Posto", 15.0, 25.0, LocalDateTime.now())
        );

        when(repository.findLocalRef(5.0, 25.0, 15.0, 35.0)).thenReturn(models);

        //Act
        List<PontoDeInteresseDTO> results = service.searchPOI(15.0, 25.0, 10.0);

        //Assert
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        assertEquals("Lanchonete", results.get(0).localPoi());

        verifyNoMoreInteractions(repository);
    }
}
