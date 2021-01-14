package progi.projekt.service;

import progi.projekt.model.Lajk;
import progi.projekt.model.LajkId;
import progi.projekt.model.Oglas;
import progi.projekt.model.Student;

import java.util.List;
import java.util.Optional;

public interface LajkService {

    List<Lajk> listAll();

    Optional<Lajk> findLajk(LajkId lajkId);

    Optional<Lajk> findLajk(Student student, Oglas oglas);

	Optional<Lajk> findLajkDvaOglasa(Oglas oglas1, Oglas oglas2);

	Lajk update(Lajk l);

    Lajk delete(Lajk l);

	void save(Lajk lajk);

	void ponistiLajkoveOglasa(Oglas oglas);
}
