package progi.projekt.service;

import progi.projekt.model.Grad;
import progi.projekt.model.Soba;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SobaService {
    Optional<Soba> setFromStudentUsernameAndPaviljonId(Soba soba, String studentId, String paviljonId);

    Optional<Soba> getByStudentUsername(String username);

    Optional<Soba> getByStudentId(UUID id);

    Optional<Soba> getById(UUID id);

    List<Grad> findAllGrad();

    Soba save(Soba soba);

    Soba update(Soba soba);
}



