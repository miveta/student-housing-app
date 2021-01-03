package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.projekt.model.Oglas;
import progi.projekt.repository.OglasRepository;
import progi.projekt.service.OglasService;

import java.util.List;
import java.util.UUID;

@Service
public class OglasServiceImpl implements OglasService {

    @Autowired
    private OglasRepository oglasRepo;

    @Override
    public List<Oglas> listAll() {
        return oglasRepo.findAll();
    }

    @Override
    public Oglas findById(UUID oglasUUID) {
        return oglasRepo.findById(oglasUUID);
    }
}
