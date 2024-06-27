package ics.luizalabs.desafio.xy_inc;

import ics.luizalabs.desafio.xy_inc.model.PontoDeInteresseModel;
import ics.luizalabs.desafio.xy_inc.repository.PontoDeInteresseRepository;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PontosDeInteresseConfigurationTests {

    @LocalServerPort
    private Integer port;

    @Autowired
    private PontoDeInteresseRepository repository;

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:15"));

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);

        log.info("url {}", container.getJdbcUrl());
        log.info("username {}", container.getUsername());
        log.info("password {}", container.getPassword());
        log.info("spring.datasource.driver-class-name {}", container.getJdbcDriverInstance());
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        repository.deleteAll();
    }

    @BeforeAll
    static void beforeAll() {
        container.start();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @Test
    void testDomain() {
        Long id = 1L;
        String local = "Lanchonete";
        Double cooodX = 10.0;
        Double coordY = 20.0;
        LocalDateTime ldt = LocalDateTime.now();

        PontoDeInteresseModel model = new PontoDeInteresseModel(id, local, cooodX, coordY, ldt);

        repository.save(model);

        assertNotNull(model);
        assertEquals(id, model.getId());
        assertEquals(local, model.getLocalPoi());
        assertEquals(cooodX, model.getCoordX());
        assertEquals(coordY, model.getCoordY());
        assertEquals(ldt, model.getCriadoEm());
    }
}
