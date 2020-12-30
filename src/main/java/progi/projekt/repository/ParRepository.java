package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Par;

public interface ParRepository extends JpaRepository<Par, Long> {
}
