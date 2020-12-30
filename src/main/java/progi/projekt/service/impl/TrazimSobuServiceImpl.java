package progi.projekt.service.impl;

import progi.projekt.model.Dom;
import progi.projekt.model.Grad;
import progi.projekt.model.Paviljon;
import progi.projekt.model.Student;
import progi.projekt.repository.GradRepository;
import progi.projekt.repository.StudentRepository;
import progi.projekt.service.TrazimSobuService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class TrazimSobuServiceImpl implements TrazimSobuService {
    private Student student;
    @Autowired
    GradRepository gradRepository;

    @Autowired
    StudentRepository studentRepository;



    @Override
    public Grad findGrad(String username) {
        student = studentRepository.findByKorisnickoIme(username);
        Grad grad = student.getGrad();
        grad = gradRepository.getOne(grad.getId());
        return grad;
    }

    @Override
    public Set<Dom> findAllDom() {
        Grad grad = student.getGrad();
        grad = gradRepository.getOne(grad.getId());
        return grad.getDomovi();
    }

    @Override
    public Set<Paviljon> findAllPaviljon() {
        Set<Dom> domovi = student.getGrad().getDomovi();
        Set<Paviljon> paviljoni = new TreeSet<>();
        for (Dom dom : domovi){
            paviljoni.addAll(dom.getPaviljoni());
        }
        return  paviljoni;
    }
}
