package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progi.projekt.model.Oglas;
import progi.projekt.model.Par;

import java.util.LinkedList;
import java.util.Optional;

@Repository
public interface ParRepository extends JpaRepository<Par, Long> {
	LinkedList<Par> findAllByOglas1OrOglas2(Oglas oglas1, Oglas oglas2);

	Optional<Par> findByIdPar(Integer idPar);
}
