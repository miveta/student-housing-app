package progi.projekt.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import progi.projekt.model.Student;
import progi.projekt.repository.StudentRepository;
import progi.projekt.security.SavingException;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> listAll();
    StudentRepository getRepo();

	Student findByJmbag(String username) throws UsernameNotFoundException;

	Student findBykorisnickoIme(String username) throws UsernameNotFoundException;

	String getLozinka(Student student) throws UsernameNotFoundException;

	boolean isElevated(Student student) throws UsernameNotFoundException;

	Student createStudent(Student student) throws SavingException;
}
