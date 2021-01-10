package progi.projekt.service;

import progi.projekt.model.Soba;
import progi.projekt.model.Student;

import java.util.Optional;
import java.util.UUID;

public interface SobaService {
    Soba setFromStudentUsernameAndPaviljonId(Soba soba, String studentId, String paviljonId);

    Soba setPaviljon(Soba soba, String paviljonId);

    Optional<Soba> getByStudentUsername(String username);

    Optional<Soba> getByStudentId(UUID id);

    Optional<Soba> getByStudent(Student student);

    Optional<Soba> getById(UUID id);

    Soba save(Soba soba);

    Soba update(Soba soba);
}



