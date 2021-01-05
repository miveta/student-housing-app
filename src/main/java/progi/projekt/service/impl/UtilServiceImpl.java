package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Paviljon;
import progi.projekt.repository.PaviljonRepository;
import progi.projekt.service.UtilService;

import java.util.Optional;
import java.util.UUID;

@Service
public class UtilServiceImpl implements UtilService {
    private PaviljonRepository paviljonRepository;

    public UtilServiceImpl(PaviljonRepository paviljonRepository) {
        this.paviljonRepository = paviljonRepository;
    }

    @Override
    public Optional<Paviljon> getPaviljonById(String id) {
        return getPaviljonById(UUID.fromString(id));
    }

    @Override
    public Optional<Paviljon> getPaviljonById(UUID id) {
        return paviljonRepository.findById(id);
    }
}
