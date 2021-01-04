package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progi.projekt.model.Soba;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SobaRepository extends JpaRepository<Soba, UUID> {
    @Override
    Optional<Soba> findById(UUID uuid);
}
