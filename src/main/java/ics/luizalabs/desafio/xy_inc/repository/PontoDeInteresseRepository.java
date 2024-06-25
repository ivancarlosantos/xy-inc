package ics.luizalabs.desafio.xy_inc.repository;

import ics.luizalabs.desafio.xy_inc.model.PontoDeInteresseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontoDeInteresseRepository extends JpaRepository<PontoDeInteresseModel, Long> {
}
