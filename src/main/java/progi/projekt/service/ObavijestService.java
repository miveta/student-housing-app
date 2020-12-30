package progi.projekt.service;

import progi.projekt.model.Obavijest;
import progi.projekt.model.Oglas;
import progi.projekt.model.Student;
import progi.projekt.model.ZaposlenikSC;
import progi.projekt.repository.ObavijestRepository;
import progi.projekt.repository.ZaposlenikscRepository;

import java.util.List;
import java.util.Optional;

public interface ObavijestService {
    List<Obavijest> listAll();

    ObavijestRepository getRepo();

    Optional<List<Obavijest>> findByStudent(Student student);

    Optional<List<Obavijest>> findByOglas(Oglas oglas);
}
