package ics.luizalabs.desafio.xy_inc.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@NoArgsConstructor
public class ExceptionMessage {

    private Integer statusNumber;
    private HttpStatus errorStatus;
    private String timestamp;
    private String message;

    public ExceptionMessage(Integer statusNumber, HttpStatus errorStatus, Date timeError, String message) {
        this.statusNumber = statusNumber;
        this.errorStatus = errorStatus;
        this.timestamp = timeError.toString();
        this.message = message;
    }
}
