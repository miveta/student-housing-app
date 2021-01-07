package progi.projekt.service;


import progi.projekt.model.Dom;
import progi.projekt.model.Grad;
import progi.projekt.model.Paviljon;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UtilService {
    Optional<Paviljon> getPaviljonById(String id);

    Optional<Paviljon> getPaviljonById(UUID id);

    Optional<Grad> getGradByNaziv(String naziv);

    Optional<Dom> getDomById(String id);

    Optional<Dom> getDomById(UUID id);

    List<Grad> findAllGrad();
}
