package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.projekt.model.Student;
import progi.projekt.repository.StudentRepository;
import progi.projekt.security.exception.SavingException;
import progi.projekt.service.StudentService;

import java.util.List;
import java.util.Optional;

//Ukoliko se provjera danih podataka radi preko asserta, assert baca IllegalArgumentException

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentRepository getRepo() {
        return studentRepository;
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        try {
            Optional<Student> opt = Optional.of(studentRepository.findByEmail(email));
            return opt;
        } catch (Exception e) {
            //studentRepo baca exceptione koje mu proslijedi baza (e)?
            String originalMessage = e.getMessage();
            //throw new JmbagNotFoundException("No user with email: '" + email + "'");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Student> findBykorisnickoIme(String username) {
        try {
            Optional<Student> opt = Optional.of(studentRepository.findByKorisnickoIme(username));
            return opt;
        } catch (Exception e) {
            //studentRepo baca exceptione koje mu proslijedi baza (e)?
            String originalMessage = e.getMessage();
            //throw new UsernameNotFoundException("No user with username: '" + username + "'");
            return Optional.empty();
        }
    }

    @Override
    public String getLozinka(Student student) {
        return student.getLozinka();
    }

    @Override
    public Student createStudent(Student student) throws SavingException {
        try {
            return studentRepository.saveAndFlush(student);
        } catch (Exception e) {
            //studentRepo baca exceptione koje mu proslijedi baza (e)?
            String originalMessage = e.getMessage();
            throw new SavingException("Exception while saving user. Original message: '" + originalMessage + "'");
        }
    }
}
