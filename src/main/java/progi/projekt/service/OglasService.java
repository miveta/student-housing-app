package progi.projekt.service;

import progi.projekt.model.Oglas;

import java.util.List;
import java.util.UUID;

public interface OglasService {
    List<Oglas> listAll();
    Oglas findById (UUID oglasUUID);
}
