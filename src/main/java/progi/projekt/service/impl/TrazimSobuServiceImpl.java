package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.projekt.model.*;
import progi.projekt.repository.DomRepository;
import progi.projekt.repository.GradRepository;
import progi.projekt.repository.StudentRepository;
import progi.projekt.repository.TrazeniUvjetiRepository;
import progi.projekt.service.TrazimSobuService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrazimSobuServiceImpl implements TrazimSobuService {

    @Autowired
    GradRepository gradRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TrazeniUvjetiRepository trazeniUvjetiRepository;

    @Override
    public Grad findGrad(String username){
        Optional<Student> student = studentRepository.findByKorisnickoIme(username);
        return student.get().getGrad();
    }

    @Override
    public void update(TrazeniUvjeti uvjeti) {
        trazeniUvjetiRepository.save(uvjeti);
    }


    @Override
    public Set<Dom> findAllDom(String username) {
        Optional<Student> student = studentRepository.findByKorisnickoIme(username);
        return student.get().getGrad().getDomovi();
    }

//    @Override
//    public List<Paviljon> findAllPaviljon() {
//        Set<Dom> domovi = student.getGrad().getDomovi();
//        List<Paviljon> paviljoni = new ArrayList<>();
//        for (Dom dom : domovi){
//            paviljoni.addAll(dom.getPaviljoni());
//        }
//        return  paviljoni;
//    }
}
