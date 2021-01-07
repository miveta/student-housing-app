package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Oglas;
import progi.projekt.model.Soba;
import progi.projekt.model.Student;
import progi.projekt.model.TrazeniUvjeti;
import progi.projekt.model.enums.StatusOglasaEnum;
import progi.projekt.repository.OglasRepository;
import progi.projekt.service.OglasService;
import progi.projekt.service.StudentService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OglasServiceImpl implements OglasService {
    private OglasRepository oglasRepository;
    private StudentService studentService;


    public OglasServiceImpl(OglasRepository oglasRepository, StudentService studentService) {
        this.oglasRepository = oglasRepository;
        this.studentService = studentService;
    }


    public List<Oglas> listAll() {
        return oglasRepository.findAll();
    }

    @Override
    public void save(Oglas oglas) {
        oglasRepository.save(oglas);
    }


    public Optional<Oglas> findById(String oglasId) {
        try {
            return oglasRepository.findById(UUID.fromString(oglasId));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }


    public Optional<Oglas> findByStudent(Student student) {
        return oglasRepository.findByStudent(student);
    }

    @Override
    public Optional<Oglas> findByStudentAndStatus(Student student, StatusOglasaEnum statusOglasa) {
        return oglasRepository.findByStudentAndStatusOglasa(student, statusOglasa);
    }


    public Optional<Oglas> findByStudentJmbag(String jmbag) {
        Optional<Student> optionalStudent = studentService.findByJmbag(jmbag);
        if (optionalStudent.isEmpty()) return Optional.empty();
        Student student = optionalStudent.get();

        return findByStudent(student);
    }

    public Optional<Oglas> findBySoba(Soba soba) {

        return oglasRepository.findBySoba(soba);
    }

    public Oglas spremiOglas(Student student, Soba soba, TrazeniUvjeti trazeniUvjeti) {
        Optional<Student> optionalStudent = studentService.findByKorisnickoIme(student.getKorisnickoIme());
        if (optionalStudent.isEmpty()) return null;

        if (!student.getSoba().equals(soba))
            throw new IllegalArgumentException("Soba koja se pokusava spremiti nije od tog studenta.");

        Optional<Oglas> optionalOglas = oglasRepository.findBySobaAndTrazeniUvjeti(soba, trazeniUvjeti);

        Oglas oglas;
        if (optionalOglas.isEmpty()) {
            oglas = new Oglas();
            oglas.setStudent(student);
        } else oglas = optionalOglas.get();

        oglas.setSoba(soba);
        oglas.setTrazeniUvjeti(trazeniUvjeti);
        oglas.setObjavljen(Date.valueOf(LocalDate.now()));

        oglas = oglasRepository.save(oglas);
        return oglas;
    }

}
