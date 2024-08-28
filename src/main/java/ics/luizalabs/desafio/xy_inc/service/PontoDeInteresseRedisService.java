package ics.luizalabs.desafio.xy_inc.service;

import ics.luizalabs.desafio.xy_inc.dto.RequestTest;
import ics.luizalabs.desafio.xy_inc.exceptions.RegraDeNegocioException;
import ics.luizalabs.desafio.xy_inc.model.PontoDeInteresseModel;
import ics.luizalabs.desafio.xy_inc.model.PontoDeInteresseRedis;
import ics.luizalabs.desafio.xy_inc.repository.PontoDeInteresseRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.*;

import static ics.luizalabs.desafio.xy_inc.configuration.RabbitMqConfig.*;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class PontoDeInteresseRedisService {

    private final int MINUTO = 2000 * 60;

    private final long MINUTOS = MINUTO;

    private final RabbitTemplate rabbitTemplate;

    private final PontoDeInteresseRedisRepository redisRepository;

    private final PontoDeInteresseService pontoDeInteresseService;


    @RabbitListener(queues = QUEUE)
    public void listen(PontoDeInteresseRedis redis) {

        System.out.println("================================");
        System.out.println("receiver: " + redis.getLocalPoi());
        redisRepository.save(redis);
    }

    public PontoDeInteresseRedis persist(PontoDeInteresseRedis redis) {

        if (redis.getCoordX() < 0 || redis.getCoordY() < 0) {
            throw new RegraDeNegocioException("COORDENADAS NÃO PODEM SER VALORES NEGATIVOS");
        }

        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, redis);

        return redis;
    }

    public List<PontoDeInteresseRedis> list() {
        return (List<PontoDeInteresseRedis>) redisRepository.findAll();
    }

    @Scheduled(fixedDelay = MINUTOS)
    private void synchronizedRedis() {
        List<PontoDeInteresseRedis> pontoDeInteresseRedisList = list();

        if (CollectionUtils.isEmpty(pontoDeInteresseRedisList)) {
            System.out.println("Lista vazia e/ou nula");
        } else {
            List<PontoDeInteresseModel> modelList = new ArrayList<>();
            pontoDeInteresseRedisList
                    .forEach(poiRedis -> {

                        PontoDeInteresseModel model = new PontoDeInteresseModel(null, poiRedis.getLocalPoi(), poiRedis.getCoordX(), poiRedis.getCoordY(), LocalDateTime.now());
                        modelList.add(model);
                        System.out.println("==============================");
                        System.out.println("local:  " + model.getLocalPoi());
                        System.out.println("coordX: " + model.getCoordX());
                        System.out.println("coordY: " + model.getCoordY());
                    });

            pontoDeInteresseService.saveAll(modelList);
            redisRepository.deleteAll(pontoDeInteresseRedisList);
        }
    }

    public List<PontoDeInteresseRedis> findLocalPOI(String local) {

        return redisRepository
                .findByLocalPOI(local)
                .stream()
                .toList();
    }

    public List<PontoDeInteresseRedis> searchPOI(Double x, Double y, Double max) {

        Double xMin = x - max;
        Double xMax = x + max;
        Double yMin = y - max;
        Double yMax = y + max;

        return redisRepository.findLocalRef(xMin, xMax, yMin, yMax)
                .stream()
                .filter(p -> searchByCoordinate(x, y, p.getCoordX(), p.getCoordY()) <= max)
                .toList();
    }

    public RequestTest test() throws UnknownHostException {

        return RequestTest
                .builder()
                .ownerHost(InetAddress.getLocalHost().getHostName())
                .address(InetAddress.getLocalHost().getHostAddress())
                .date(new Date().toString())
                .build();
    }

    private Double searchByCoordinate(Double coordX,
                                      Double coordY,
                                      Double refX,
                                      Double refY) {

        return calcRange(coordX, coordY, refX, refY);
    }

    private Double calcRange(Double coordX, Double coordY, Double refX, Double refY) {
        return Math.sqrt(Math.pow(refX - coordX, 2) + Math.pow(refY - coordY, 2));
    }

}
