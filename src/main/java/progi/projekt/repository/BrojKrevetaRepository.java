package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.BrojKreveta;


public interface BrojKrevetaRepository extends JpaRepository<BrojKreveta, Long> {
    public BrojKreveta findByNaziv(String naziv);
}
