package progi.projekt.service.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import progi.projekt.model.Obavijest;
import progi.projekt.model.Student;
import progi.projekt.repository.StudentRepository;
import progi.projekt.security.exception.SavingException;
import progi.projekt.service.StudentService;

import java.util.List;
import java.util.Optional;

//Ukoliko se provjera danih podataka radi preko asserta, assert baca IllegalArgumentException

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        try {
            return studentRepository.findByEmail(email);
        } catch (Exception e) {
            //studentRepo baca exceptione koje mu proslijedi baza (e)?
            String originalMessage = e.getMessage();
            //throw new JmbagNotFoundException("No user with email: '" + email + "'");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Student> findByKorisnickoIme(String username) {
        try {
            return studentRepository.findByKorisnickoIme(username);
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
            throw new SavingException("Exception while saving user. Original message: '" + e.getMessage() + "'");
        }
    }

    @Override
    public boolean studentExists(String username) throws UsernameNotFoundException {
        return findByKorisnickoIme(username).isPresent();
    }

    @Override
    public List<Obavijest> getObavijest(String username) {
        Optional<Student> student = findByKorisnickoIme(username);

        if (student.isEmpty()) {
            throw new UsernameNotFoundException("Student s tim korisničkim imenom ne postoji!");
        }

        return student.get().getObavijesti();
    }
}
