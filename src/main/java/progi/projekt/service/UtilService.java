package progi.projekt.service;

import progi.projekt.model.Paviljon;

import java.util.Optional;
import java.util.UUID;

public interface UtilService {
    Optional<Paviljon> getPaviljonById(String id);

    Optional<Paviljon> getPaviljonById(UUID id);

}
