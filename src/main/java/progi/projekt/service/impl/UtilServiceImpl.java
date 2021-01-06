package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Dom;
import progi.projekt.model.Paviljon;
import progi.projekt.repository.DomRepository;
import progi.projekt.repository.PaviljonRepository;
import progi.projekt.service.UtilService;

import java.util.Optional;
import java.util.UUID;

@Service
public class UtilServiceImpl implements UtilService {
    private PaviljonRepository paviljonRepository;
    private DomRepository domRepository;

    public UtilServiceImpl(PaviljonRepository paviljonRepository, DomRepository domRepository) {
        this.paviljonRepository = paviljonRepository;
        this.domRepository = domRepository;
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
    public Optional<Dom> getDomById(String id) {
        return getDomById(UUID.fromString(id));
    }

    @Override
    public Optional<Dom> getDomById(UUID id) {

        return domRepository.findById(id);
    }
}
