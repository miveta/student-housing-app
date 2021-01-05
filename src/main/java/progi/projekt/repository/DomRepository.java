package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Dom;

import java.util.UUID;

public interface DomRepository extends JpaRepository<Dom, Long> {
    Dom findById(UUID id);
}
