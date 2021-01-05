package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Grad;

import java.util.UUID;

public interface GradRepository extends JpaRepository<Grad, UUID> {
    //List<Grad> findAll();
}
