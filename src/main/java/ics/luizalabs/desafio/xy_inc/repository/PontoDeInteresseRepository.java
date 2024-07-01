package ics.luizalabs.desafio.xy_inc.repository;

import ics.luizalabs.desafio.xy_inc.model.PontoDeInteresseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PontoDeInteresseRepository extends JpaRepository<PontoDeInteresseModel, Long> {

    @Query(value = "SELECT * FROM tb_poi u WHERE u.local_poi LIKE %?1%", nativeQuery = true)
    List<PontoDeInteresseModel> findByLocalPOI(@Param("local") String local);

    @Query("SELECT p FROM PontoDeInteresseModel p WHERE (p.coordX >= :xMin AND p.coordX <= :xMax AND p.coordY >= :yMin AND p.coordY <= :yMax)")
    List<PontoDeInteresseModel> findLocalRef(
                                     @Param("xMin")  Double xMin,
                                     @Param("xMax")  Double xMax,
                                     @Param("yMin")  Double yMin,
                                     @Param("yMax")  Double yMax);
}
