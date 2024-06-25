package ics.luizalabs.desafio.xy_inc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;

@Slf4j
@SpringBootApplication
public class PontosDeInteresseApplication {

	public static void main(String[] args) {
		SpringApplication.run(PontosDeInteresseApplication.class, args);
        log.info("Application UP [{}]", HttpStatus.OK);
	}
}
