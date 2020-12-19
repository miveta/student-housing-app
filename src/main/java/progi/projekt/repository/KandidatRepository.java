package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Kandidat;

public interface KandidatRepository extends JpaRepository<Kandidat, Long> {
}
