package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import progi.projekt.model.Student;
import progi.projekt.repository.StudentRepository;
import progi.projekt.security.JmbagNotFoundException;
import progi.projekt.security.SavingException;
import progi.projekt.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public StudentRepository getRepo() {return studentRepository; }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    //Provjera danih podataka preko asserta baca IllegalArgumentException

    @Override
    public Student findByJmbag(String jmbag) throws UsernameNotFoundException {
        try {
            Student rezultat = studentRepository.findByJmbag(jmbag);
            return rezultat;
        }
        catch (Exception e){
            //studentRepo baca exceptione koje mu proslijedi baza (e)?
            String originalMessage = e.getMessage();
            throw new JmbagNotFoundException("No user with JMBAG: '" + jmbag + "'");
        }

    }

    @Override
    public Student findBykorisnickoIme(String username) throws UsernameNotFoundException {
        try {
            Student rezultat = studentRepository.findByKorisnickoIme(username);
            return rezultat;
        }
        catch (Exception e){
            //studentRepo baca exceptione koje mu proslijedi baza (e)?
            String originalMessage = e.getMessage();
            throw new UsernameNotFoundException("No user with username: '" + username + "'");
        }
    }

    @Override
    public String getLozinka(Student student) {
        return student.getLozinka();
    }

    @Override
    public boolean isElevated(Student student) {
        return student.isElevated();
    }

    @Override
    public Student createStudent(Student student) throws SavingException{
        try {
            Student rezultat = studentRepository.saveAndFlush(student);
            return rezultat;
        }
        catch (Exception e){
            //studentRepo baca exceptione koje mu proslijedi baza (e)?
            String originalMessage = e.getMessage();
            throw new SavingException("Exception while saving user. Original message: '" + originalMessage + "'");
        }
    }
}
