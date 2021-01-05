package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Paviljon;

import java.util.Optional;
import java.util.UUID;

public interface PaviljonRepository extends JpaRepository<Paviljon, UUID> {
    @Override
    Optional<Paviljon> findById(UUID uuid);
}
