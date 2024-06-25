package ics.luizalabs.desafio.xy_inc.repository;

import ics.luizalabs.desafio.xy_inc.model.PontoDeInteresseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PontoDeInteresseRepository extends JpaRepository<PontoDeInteresseModel, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM tb_poi u WHERE u.local_poi LIKE %?1%")
    Optional<PontoDeInteresseModel> findByLocalPOI(@Param(value = "local_poi") String local);

    @Query("SELECT p FROM PontoDeInteresseModel p WHERE (p.coordX >= :xMin AND p.coordX <= :xMax AND p.coordY >= :yMin AND p.coordY <= :yMax)")
    List<PontoDeInteresseModel> findLocalRef(
                                     @Param("xMin")  Double xMin,
                                     @Param("xMax")  Double xMax,
                                     @Param("yMin")  Double yMin,
                                     @Param("yMax")  Double yMax);
}
