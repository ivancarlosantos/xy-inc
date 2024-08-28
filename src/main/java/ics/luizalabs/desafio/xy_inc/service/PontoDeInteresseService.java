package ics.luizalabs.desafio.xy_inc.service;

import ics.luizalabs.desafio.xy_inc.dto.PontoDeInteresseDTO;
import ics.luizalabs.desafio.xy_inc.dto.RequestTest;
import ics.luizalabs.desafio.xy_inc.exceptions.RegraDeNegocioException;
import ics.luizalabs.desafio.xy_inc.exceptions.LocalNaoEncontradoException;
import ics.luizalabs.desafio.xy_inc.model.PontoDeInteresseModel;
import ics.luizalabs.desafio.xy_inc.repository.PontoDeInteresseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PontoDeInteresseService {

    private final PontoDeInteresseRepository repository;

    public PontoDeInteresseDTO persist(PontoDeInteresseDTO dto) {

        PontoDeInteresseModel poi = PontoDeInteresseModel
                .builder()
                .localPoi(dto.localPoi())
                .coordX(dto.coordX())
                .coordY(dto.coordY())
                .build();

        if (dto.coordX() < 0 || dto.coordY() < 0) {
            throw new RegraDeNegocioException("COORDENADAS NÃO PODEM SER VALORES NEGATIVOS");
        }

        repository.save(poi);

        return new PontoDeInteresseDTO(poi.getId(), poi.getLocalPoi(), poi.getCoordX(), poi.getCoordY());
    }

    public PontoDeInteresseDTO updatePOI(Long id, PontoDeInteresseDTO dto) {

        PontoDeInteresseModel model = findID(id);
        model.setLocalPoi(dto.localPoi());
        model.setCoordX(dto.coordX());
        model.setCoordY(dto.coordY());

        if (dto.coordX() < 0 || dto.coordY() < 0) {
            throw new RegraDeNegocioException("COORDENADAS NÃO PODEM SER VALORES NEGATIVOS");
        }

        PontoDeInteresseModel resp = repository.save(model);

        return new PontoDeInteresseDTO(resp.getId(), resp.getLocalPoi(), resp.getCoordX(), resp.getCoordY());
    }

    public List<PontoDeInteresseDTO> list() {
        return repository
                .findAll()
                .stream()
                .map(poi -> new PontoDeInteresseDTO(poi.getId(), poi.getLocalPoi(), poi.getCoordX(), poi.getCoordY()))
                .toList();
    }

    public List<PontoDeInteresseDTO> findLocalPOI(String local) {

        return repository
                .findByLocalPOI(local)
                .stream()
                .map(poi -> new PontoDeInteresseDTO(poi.getId(), poi.getLocalPoi(), poi.getCoordX(), poi.getCoordY()))
                .toList();
    }

    public List<PontoDeInteresseDTO> searchPOI(Double x, Double y, Double max) {

        Double xMin = x - max;
        Double xMax = x + max;
        Double yMin = y - max;
        Double yMax = y + max;

        return repository.findLocalRef(xMin, xMax, yMin, yMax)
                .stream()
                .filter(p -> searchByCoordinate(x, y, p.getCoordX(), p.getCoordY()) <= max)
                .map(poi -> new PontoDeInteresseDTO(poi.getId(), poi.getLocalPoi(), poi.getCoordX(), poi.getCoordY()))
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

    private PontoDeInteresseModel findID(Long id) {
        Optional<PontoDeInteresseModel> findID = Optional
                .ofNullable(repository
                        .findById(id)
                        .orElseThrow(() -> new LocalNaoEncontradoException("LOCAL NÃO ENCONTRADO")));

        PontoDeInteresseModel model = null;

        model = findID.get();

        return model;
    }

    private Double calcRange(Double coordX, Double coordY, Double refX, Double refY) {
        return Math.sqrt(Math.pow(refX - coordX, 2) + Math.pow(refY - coordY, 2));
    }

    public void saveAll(List<PontoDeInteresseModel> modelList) {
        repository.saveAll(modelList);
    }
}
