package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Paviljon;

public interface PaviljonRepository extends JpaRepository<Paviljon, Long> {
    public Paviljon findByNaziv(String naziv);
}
