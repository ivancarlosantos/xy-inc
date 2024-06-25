package ics.luizalabs.desafio.xy_inc.service;

import ics.luizalabs.desafio.xy_inc.dto.PontoDeInteresseDTO;
import ics.luizalabs.desafio.xy_inc.exceptions.BusinessException;
import ics.luizalabs.desafio.xy_inc.exceptions.LocalNaoEncontradoException;
import ics.luizalabs.desafio.xy_inc.model.PontoDeInteresseModel;
import ics.luizalabs.desafio.xy_inc.repository.PontoDeInteresseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PontoDeInteresseService {

    private final ModelMapper mapper;

    private final PontoDeInteresseRepository repository;

    public PontoDeInteresseDTO persist(PontoDeInteresseDTO dto) {

        PontoDeInteresseModel poi = PontoDeInteresseModel
                .builder()
                .localPoi(dto.getLocalPoi())
                .coordX(dto.getCoordX())
                .coordY(dto.getCoordY())
                .criadoEm(LocalDateTime.now())
                .build();

        if (dto.getCoordX() < 0 || dto.getCoordY() < 0) {
            throw new BusinessException("COORDENADAS NÃO PODEM SER VALORES NEGATIVOS");
        }

        repository.save(poi);

        return mapper.map(poi, PontoDeInteresseDTO.class);
    }

    public List<PontoDeInteresseDTO> list() {
        return repository
                .findAll(Sort.by("localPoi"))
                .stream()
                .map(poi -> mapper.map(poi, PontoDeInteresseDTO.class))
                .toList();
    }

    public PontoDeInteresseDTO foundLocalPOI(String local) {

        Optional<PontoDeInteresseDTO> pdi = Optional
                .ofNullable(repository
                        .findByLocalPOI(local)
                        .map(poi -> mapper.map(poi, PontoDeInteresseDTO.class))
                        .orElseThrow(() -> new LocalNaoEncontradoException("LOCAL NÃO ENCONTRADO")))
                .stream()
                .findFirst();

        return pdi.get();
    }

    public List<PontoDeInteresseDTO> searchPOI(Double x, Double y, Double max) {

        Double xMin = x - max;
        Double xMax = x + max;
        Double yMin = y - max;
        Double yMax = y + max;

        return repository.findLocalRef(xMin, xMax, yMin, yMax)
                .stream()
                .filter(p -> searchByCoordinate(x, y, p.getCoordX(), p.getCoordY()) <= max)
                .map(poi -> mapper.map(poi, PontoDeInteresseDTO.class))
                .toList();
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
