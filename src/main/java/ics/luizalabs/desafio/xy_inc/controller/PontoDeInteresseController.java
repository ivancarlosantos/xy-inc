package ics.luizalabs.desafio.xy_inc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.Date;

@RestController
@RequestMapping(path = "/poi")
public class PontoDeInteresseController {


    @GetMapping(path = "/test")
    public ResponseEntity<String> test() {
        String status = InetAddress.getLoopbackAddress().getHostAddress() + " - " + new Date();
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }
}
