package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.projekt.model.Obavijest;
import progi.projekt.model.Oglas;
import progi.projekt.model.Student;
import progi.projekt.repository.ObavijestRepository;
import progi.projekt.service.ObavijestService;
import progi.projekt.service.StudentService;

import java.util.List;
import java.util.Optional;

@Service
public class ObavijestServiceImpl implements ObavijestService {
    @Autowired
    private StudentService studentService;

    @Override
    public List<Obavijest> listAll() {
        return null;
    }

    @Override
    public ObavijestRepository getRepo() {
        return null;
    }

    @Override
    public Optional<List<Obavijest>> findByStudent(Student student) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Obavijest>> findByOglas(Oglas oglas) {
        return Optional.empty();
    }
}
