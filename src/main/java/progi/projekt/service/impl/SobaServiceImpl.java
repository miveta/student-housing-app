package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Paviljon;
import progi.projekt.model.Soba;
import progi.projekt.model.Student;
import progi.projekt.repository.GradRepository;
import progi.projekt.repository.SobaRepository;
import progi.projekt.repository.StudentRepository;
import progi.projekt.service.SobaService;
import progi.projekt.service.StudentService;
import progi.projekt.service.UtilService;

import java.util.Optional;
import java.util.UUID;

@Service
public class SobaServiceImpl implements SobaService {

    private SobaRepository sobaRepository;
    private GradRepository gradRepository;
    private StudentRepository studentRepository;
    private StudentService studentService;
    private UtilService utilService;

    public SobaServiceImpl(SobaRepository sobaRepository, GradRepository gradRepository, StudentRepository studentRepository, StudentService studentService, UtilService utilService) {
        this.sobaRepository = sobaRepository;
        this.gradRepository = gradRepository;
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.utilService = utilService;
    }

    @Override
    public Soba save(Soba soba) {
        return sobaRepository.saveAndFlush(soba);
    }

    @Override
    public Soba update(Soba soba) {
        return null;
    }

    @Override
    public Optional<Soba> getByStudentUsername(String username) {
        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(username);
        if (optionalStudent.isEmpty()) return Optional.empty();

        Student student = optionalStudent.get();

        if (student.getSoba() != null) return Optional.of(student.getSoba());
        return Optional.empty();
    }

    @Override
    public Soba setFromStudentUsernameAndPaviljonId(Soba soba, String studentUsername, String paviljonId) {
        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(studentUsername);
        Optional<Paviljon> optionalPaviljon = utilService.getPaviljonById(paviljonId);

        if (optionalStudent.isEmpty() || optionalPaviljon.isEmpty()) return null;

        soba.setStudent(optionalStudent.get());
        soba.setPaviljon(optionalPaviljon.get());

        return soba;
    }

    @Override
    public Optional<Soba> getByStudentId(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Soba> getByStudent(Student student) {
        return Optional.empty();
    }

    @Override
    public Optional<Soba> getById(UUID id) {
        return sobaRepository.findById(id);
    }
}
