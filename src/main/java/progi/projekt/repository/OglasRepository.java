package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Oglas;

public interface OglasRepository extends JpaRepository<Oglas, Long> {
}
