package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Grad;
import progi.projekt.model.Soba;
import progi.projekt.model.Student;
import progi.projekt.repository.GradRepository;
import progi.projekt.repository.SobaRepository;
import progi.projekt.repository.StudentRepository;
import progi.projekt.service.SobaService;
import progi.projekt.service.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SobaServiceImpl implements SobaService {

    private SobaRepository sobaRepository;


    private GradRepository gradRepository;
    private StudentRepository studentRepository;
    private StudentService studentService;

    public SobaServiceImpl(SobaRepository sobaRepository, GradRepository gradRepository, StudentRepository studentRepository, StudentService studentService) {
        this.sobaRepository = sobaRepository;
        this.gradRepository = gradRepository;
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    public List<Grad> findAllGrad() {
        return gradRepository.findAll();
    }

    @Override
    public Optional<Soba> getByStudentUsername(String username) {
        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(username);
        if (optionalStudent.isEmpty()) return Optional.empty();

        Student student = optionalStudent.get();

        return sobaRepository.findByStudent(student);
    }

    @Override
    public Optional<Soba> getByStudentId(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Soba> getById(UUID id) {
        return sobaRepository.findById(id);
    }
}
