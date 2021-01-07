package progi.projekt.service;

import progi.projekt.model.Oglas;
import progi.projekt.model.Soba;
import progi.projekt.model.Student;
import progi.projekt.model.TrazeniUvjeti;
import progi.projekt.model.enums.StatusOglasaEnum;

import java.util.List;
import java.util.Optional;

public interface OglasService {
    List<Oglas> listAll();

    void save (Oglas oglas);

    Optional<Oglas> findById(String id);

    Oglas spremiOglas(Student student, Soba soba, TrazeniUvjeti trazeniUvjeti);
    //Optional<Oglas> kreirajOglas();

    Optional<Oglas> findBySoba(Soba soba);

    Optional<Oglas> findByStudentJmbag(String jmbag);

    Optional<Oglas> findByStudent(Student student);

    Optional<Oglas> findByStudentAndStatus(Student student, StatusOglasaEnum statusOglasa);
}
