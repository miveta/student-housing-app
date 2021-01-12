package progi.projekt.service.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import progi.projekt.model.Obavijest;
import progi.projekt.model.Oglas;
import progi.projekt.model.Student;
import progi.projekt.model.enums.StatusOglasaEnum;
import progi.projekt.repository.StudentRepository;
import progi.projekt.security.exception.SavingException;
import progi.projekt.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;

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
    public Optional<Student> findById(String id) {
        return findById(UUID.fromString(id));
    }

    @Override
    public Optional<Student> findById(UUID id) {
        return studentRepository.findById(id);
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
    public Optional<Student> findByJmbag(String jmbag) {
        try {
            return studentRepository.findByJmbag(jmbag);
        } catch (Exception e) {
            //studentRepo baca exceptione koje mu proslijedi baza (e)?
            String originalMessage = e.getMessage();
            //throw new JmbagNotFoundException("No user with jmbag: '" + jmbag + "'");
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

    @Override
    public boolean arhiviraj(String username, String oglasId) {
        Optional<Student> optionalStudent = findByKorisnickoIme(username);
        if (optionalStudent.isEmpty())
            throw new UsernameNotFoundException("Student s tim korisničkim imenom ne postoji!");
        Student student = optionalStudent.get();

        Oglas aktivniOglas = student.getAktivniOglas();

        if (aktivniOglas == null) return false;
        if (!aktivniOglas.getStatusOglasa().equals(StatusOglasaEnum.AKTIVAN)) ;
        return true;
    }

    @Override
    public void save(Student stud) {
        studentRepository.save(stud);
    }

    @Override
    public List<Oglas> oglasi(String username, StatusOglasaEnum statusOglasa) {
        Optional<Student> optionalStudent = findByKorisnickoIme(username);

        if (optionalStudent.isEmpty()) {
            throw new UsernameNotFoundException("Student s tim korisničkim imenom ne postoji!");
        }

        Student student = optionalStudent.get();
        Set<Oglas> oglasi = student.getOglasi();
        if (oglasi == null) return new ArrayList<>();

        return oglasi.stream().filter(oglas -> oglas.getStatusOglasa().equals(statusOglasa)).collect(Collectors.toList());
    }

    @Override
    public Student update(Student student) throws SavingException {
        try {
            return studentRepository.saveAndFlush(student);
        } catch (Exception e) {
            //studentRepo baca exceptione koje mu proslijedi baza (e)?
            throw new SavingException("Exception while saving user. Original message: '" + e.getMessage() + "'");
        }
    }

    @Override
    public Student delete(Student student) throws SavingException {
        studentRepository.delete(student);
        return null;
    }
}
