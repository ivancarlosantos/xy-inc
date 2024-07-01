package ics.luizalabs.desafio.xy_inc.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import ics.luizalabs.desafio.xy_inc.controller.PontoDeInteresseController;
import ics.luizalabs.desafio.xy_inc.dto.PontoDeInteresseDTO;
import ics.luizalabs.desafio.xy_inc.dto.RequestTest;
import ics.luizalabs.desafio.xy_inc.model.PontoDeInteresseModel;
import ics.luizalabs.desafio.xy_inc.service.PontoDeInteresseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PontoDeInteresseController.class)
public class PontoDeInteresseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PontoDeInteresseService service;

    @Autowired
    private ObjectMapper mapper;


    @Test
    @DisplayName("Client Cadastra um Ponto De Interesse")
    void persist() throws Exception {

        PontoDeInteresseDTO dto = new PontoDeInteresseDTO("Lanchonete", 10.0, 20.0);

        PontoDeInteresseModel model = PontoDeInteresseModel.builder()
                .id(1L)
                .localPoi(dto.localPoi())
                .coordX(dto.coordX())
                .coordY(dto.coordY())
                .criadoEm(LocalDateTime.now())
                .build();

        when(service.persist(dto)).thenReturn(dto);
        String request = mapper.writeValueAsString(model);

        mockMvc.perform(MockMvcRequestBuilders.post("/poi/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.localPoi").value(dto.localPoi()))
                .andExpect(jsonPath("$.coordX").value(dto.coordX()))
                .andExpect(jsonPath("$.coordY").value(dto.coordY()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Client Atualiza Um Ponto De Interesse")
    void testUpdate() throws Exception {
        Long id = 1L;
        PontoDeInteresseDTO dto = new PontoDeInteresseDTO("Lanchonete", 10.0, 20.0);
        when(service.updatePOI(id, dto)).thenReturn(dto);

        String response = mapper.writeValueAsString(dto);

        mockMvc.perform(put("/poi/update/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(response))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.localPoi").value(dto.localPoi()))
                .andExpect(jsonPath("$.coordX").value(dto.coordX()))
                .andExpect(jsonPath("$.coordY").value(dto.coordY()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Retorna ao Client a Lista de Pontos de Interesse Cadastrados")
    void testList() throws Exception {

        PontoDeInteresseDTO dto1 = new PontoDeInteresseDTO("Lanchonete", 10.0, 20.0);
        PontoDeInteresseDTO dto2 = new PontoDeInteresseDTO("Pub", 30.0, 40.0);
        when(service.list()).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/poi/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].localPoi").value(dto1.localPoi()))
                .andExpect(jsonPath("$[0].coordX").value(dto1.coordX()))
                .andExpect(jsonPath("$[0].coordY").value(dto1.coordY()))

                .andExpect(jsonPath("$[1].localPoi").value(dto2.localPoi()))
                .andExpect(jsonPath("$[1].coordX").value(dto2.coordX()))
                .andExpect(jsonPath("$[1].coordY").value(dto2.coordY()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Client Encontra um Local Cadastrado")
    void testFindLocalPOI() throws Exception {

        PontoDeInteresseDTO dto = new PontoDeInteresseDTO("Lanchonete", 10.0, 20.0);

        when(service.findLocalPOI("Lanchonete")).thenReturn(List.of(dto));

        mockMvc.perform(get("/poi/find")
                        .param("local", "Lanchonete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].localPoi", is("Lanchonete")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Retorna ao Client uma Lista de Locais de Pontos de Interesse em relação a Distância Fornecida")
    void testSearchPOI() throws Exception {

        PontoDeInteresseDTO dto = new PontoDeInteresseDTO("Lanchonete", 10.0, 20.0);

        when(service.searchPOI(10.0, 20.0, 10.0))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/poi/search")
                        .param("x", "10.0")
                        .param("y", "20.0")
                        .param("max", "10.0"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].localPoi").value(dto.localPoi()))
                .andExpect(jsonPath("$[0].coordX").value(dto.coordX()))
                .andExpect(jsonPath("$[0].coordY").value(dto.coordY()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Endpoint de Teste para verificação de acesso a aplicação")
    void testEndpointApplication() throws Exception {

        RequestTest requestTest = RequestTest.builder()
                .ownerHost(InetAddress.getLocalHost().getHostName())
                .address(InetAddress.getLocalHost().getHostAddress())
                .date(new Date().toString())
                .build();

        when(service.test()).thenReturn(requestTest);
        String response = mapper.writeValueAsString(requestTest);

        mockMvc.perform(get("/poi/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(response))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value(requestTest.getAddress()))
                .andExpect(jsonPath("$.date").value(requestTest.getDate()))
                .andDo(MockMvcResultHandlers.print());
    }
}
