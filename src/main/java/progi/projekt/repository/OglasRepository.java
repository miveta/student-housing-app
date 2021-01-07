package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Oglas;
import progi.projekt.model.Soba;
import progi.projekt.model.Student;
import progi.projekt.model.TrazeniUvjeti;
import progi.projekt.model.enums.StatusOglasaEnum;

import java.util.Optional;
import java.util.UUID;

public interface OglasRepository extends JpaRepository<Oglas, UUID> {

    @Override
    Optional<Oglas> findById(UUID id);

    Optional<Oglas> findByStudent(Student student);

    Optional<Oglas> findByStudentAndStatusOglasa(Student student, StatusOglasaEnum statusOglasaEnum);

    Optional<Oglas> findBySobaAndTrazeniUvjeti(Soba soba, TrazeniUvjeti trazeniUvjeti);

    Optional<Oglas> findBySoba(Soba soba);
}
