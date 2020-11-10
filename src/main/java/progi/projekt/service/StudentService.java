package progi.projekt.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import progi.projekt.model.Student;
import progi.projekt.security.exception.SavingException;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> listAll();

    Optional<Student> findByEmail(String email) throws UsernameNotFoundException;

    Optional<Student> findBykorisnickoIme(String username) throws UsernameNotFoundException;

    String getLozinka(Student student);

    Student createStudent(Student student) throws SavingException;
}
