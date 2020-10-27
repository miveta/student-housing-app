package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.projekt.model.Student;
import progi.projekt.repository.StudentRepository;
import progi.projekt.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }
}
