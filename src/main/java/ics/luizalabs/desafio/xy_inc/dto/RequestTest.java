package ics.luizalabs.desafio.xy_inc.dto;

import lombok.*;

import java.net.InetAddress;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestTest {

    private String ownerHost;

    private String address;

    private String date;
}
