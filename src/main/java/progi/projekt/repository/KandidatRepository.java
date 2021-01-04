package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Kandidat;
import progi.projekt.model.Oglas;

import java.util.List;

public interface KandidatRepository extends JpaRepository<Kandidat, Long> {
	//List<Kandidat> findAllByIdKandidat(UUID oglasUuid);
	List<Kandidat> findAllByOglas(Oglas oglas);
}
