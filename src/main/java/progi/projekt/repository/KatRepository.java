package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Kat;

public interface KatRepository extends JpaRepository<Kat, Long> {
}
