package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Grad;

public interface GradRepository extends JpaRepository<Grad, Long> {
}
