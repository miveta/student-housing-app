package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.projekt.model.Oglas;
import progi.projekt.repository.OglasRepository;
import progi.projekt.service.OglasService;

import java.util.List;

@Service
public class OglasServiceImpl implements OglasService {

    @Autowired
    private OglasRepository oglasRepo;

    @Override
    public List<Oglas> listAll() {
        return oglasRepo.findAll();
    }
}
