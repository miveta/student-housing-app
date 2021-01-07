package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Dom;
import progi.projekt.model.Grad;
import progi.projekt.model.Paviljon;
import progi.projekt.repository.DomRepository;
import progi.projekt.repository.GradRepository;
import progi.projekt.repository.PaviljonRepository;
import progi.projekt.service.UtilService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtilServiceImpl implements UtilService {
    private PaviljonRepository paviljonRepository;
    private DomRepository domRepository;
    private GradRepository gradRepository;


    public UtilServiceImpl(PaviljonRepository paviljonRepository, DomRepository domRepository, GradRepository gradRepository) {
        this.paviljonRepository = paviljonRepository;
        this.domRepository = domRepository;
        this.gradRepository = gradRepository;
    }

    @Override
    public List<Grad> findAllGrad() {
        return gradRepository.findAll();
    }

    @Override
    public Optional<Paviljon> getPaviljonById(String id) {
        return getPaviljonById(UUID.fromString(id));
    }

    @Override
    public Optional<Paviljon> getPaviljonById(UUID id) {
        return paviljonRepository.findById(id);
    }

    @Override
    public Optional<Grad> getGradByNaziv(String naziv) {
        return gradRepository.findByNaziv(naziv);
    }

    @Override
    public Optional<Dom> getDomById(String id) {
        return getDomById(UUID.fromString(id));
    }

    @Override
    public Optional<Dom> getDomById(UUID id) {

        return domRepository.findById(id);
    }
}
