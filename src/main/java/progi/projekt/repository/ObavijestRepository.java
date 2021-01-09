package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Obavijest;

import java.util.UUID;

public interface ObavijestRepository extends JpaRepository<Obavijest, UUID> {

}
