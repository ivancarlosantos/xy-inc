package ics.luizalabs.desafio.xy_inc.dto;

import lombok.*;

import java.net.InetAddress;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestTest {

    private InetAddress address;

    private String date;
}
