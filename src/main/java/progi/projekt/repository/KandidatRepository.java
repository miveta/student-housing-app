package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Kandidat;

import java.util.List;
import java.util.UUID;

public interface KandidatRepository extends JpaRepository<Kandidat, Long> {
	List<Kandidat> findAllByIdKandidat(UUID oglasUuid);
	List<Kandidat> findAllByIdOglas(UUID oglasUuid);
}
