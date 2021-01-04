package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.projekt.model.Oglas;
import progi.projekt.repository.OglasRepository;
import progi.projekt.service.OglasService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OglasServiceImpl implements OglasService {

    @Autowired
    private OglasRepository oglasRepository;

    @Override
    public List<Oglas> listAll() {
        return oglasRepository.findAll();
    }

    @Override
    public Optional<Oglas> findById(String oglasId) {
        try {
            return oglasRepository.findById(UUID.fromString(oglasId));
        } catch (Exception e) {
            System.err.println(e);
            return Optional.empty();
        }
    }

    @Override
    public Oglas findById(UUID oglasUUID) {
        return oglasRepo.findById(oglasUUID);
    }
}
