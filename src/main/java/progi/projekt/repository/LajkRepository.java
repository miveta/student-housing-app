package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progi.projekt.model.Lajk;
import progi.projekt.model.LajkId;

import java.util.Optional;

@Repository
public interface LajkRepository extends JpaRepository<Lajk, LajkId> {
    Optional<Lajk> findById(LajkId lajkId);
}
