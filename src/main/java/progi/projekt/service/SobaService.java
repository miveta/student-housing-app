package progi.projekt.service;

import progi.projekt.model.Grad;
import progi.projekt.model.Soba;

import java.util.List;
import java.util.UUID;

public interface SobaService {
    Soba getByStudentId(UUID id);

    Soba getById(UUID id);

    List<Grad> findAllGrad();
}



