package ics.luizalabs.desafio.xy_inc.controllertest;

import ics.luizalabs.desafio.xy_inc.model.PontoDeInteresseRedis;
import ics.luizalabs.desafio.xy_inc.repository.PontoDeInteresseRedisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PontoDeInteresseRedisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PontoDeInteresseRedisRepository redisRepository;

    @Test
    public void testListPOIs() throws Exception {

        PontoDeInteresseRedis redis1 = new PontoDeInteresseRedis(null,"Local 1", 10.0, 20.0);
        PontoDeInteresseRedis redis2 = new PontoDeInteresseRedis(null,"Local 2", 15.0, 25.0);
        redisRepository.save(redis1);
        redisRepository.save(redis2);

        mockMvc.perform(get("/poi/list-redis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].localPoi").value("Local 1"))
                .andExpect(jsonPath("$[1].localPoi").value("Local 2"));
    }
}
