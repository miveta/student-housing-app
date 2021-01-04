package progi.projekt.service;

import progi.projekt.model.Oglas;

import java.util.List;
import java.util.Optional;

public interface OglasService {
    List<Oglas> listAll();

    Optional<Oglas> findById(String id);
}
