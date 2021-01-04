package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progi.projekt.model.Oglas;

import java.util.Optional;
import java.util.UUID;

public interface OglasRepository extends JpaRepository<Oglas, UUID> {

    @Override
    Optional<Oglas> findById(UUID id);
}
