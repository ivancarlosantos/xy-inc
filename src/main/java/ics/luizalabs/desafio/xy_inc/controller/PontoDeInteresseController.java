package ics.luizalabs.desafio.xy_inc.controller;

import ics.luizalabs.desafio.xy_inc.dto.PontoDeInteresseDTO;
import ics.luizalabs.desafio.xy_inc.dto.RequestTest;
import ics.luizalabs.desafio.xy_inc.service.PontoDeInteresseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/poi")
public class PontoDeInteresseController {

    private final PontoDeInteresseService service;

    @PostMapping(path = "/save")
    public ResponseEntity<PontoDeInteresseDTO> persist(@Valid @RequestBody PontoDeInteresseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.persist(dto));
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<PontoDeInteresseDTO>> list() {
        return ResponseEntity.status(HttpStatus.OK).body(service.list());
    }

    @GetMapping(path = "/find")
    public ResponseEntity<PontoDeInteresseDTO> foundLocalPOI(@RequestParam(value = "local") String local) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findLocalPOI(local));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PontoDeInteresseDTO>> nearMe(@RequestParam("x") Double x,
                                                            @RequestParam("y") Double y,
                                                            @RequestParam("max") Double max) {

        return ResponseEntity.status(HttpStatus.OK).body(service.searchPOI(x, y, max));
    }

    @GetMapping(path = "/test")
    public ResponseEntity<RequestTest> test() throws UnknownHostException {

        RequestTest requestTest = RequestTest.builder()
                .address(InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()))
                .date(new Date().toString())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(requestTest);
    }
}
