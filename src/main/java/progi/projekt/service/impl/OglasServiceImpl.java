package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.projekt.model.Oglas;
import progi.projekt.repository.OglasRepository;
import progi.projekt.service.OglasService;

import java.util.List;
import java.util.Optional;

@Service
public class OglasServiceImpl implements OglasService {

    @Autowired
    private OglasRepository oglasRepo;

    @Override
    public List<Oglas> listAll() {
        return oglasRepo.findAll();
    }

    @Override
    public Optional<Oglas> findById(String oglasId) {
        try {
            return Optional.of(oglasRepo.findByOglasId(Long.parseLong(oglasId)));
        } catch (Exception e) {
            //studentRepo baca exceptione koje mu proslijedi baza (e)?
            String originalMessage = e.getMessage();
            //throw new JmbagNotFoundException("No user with email: '" + email + "'");
            return Optional.empty();
        }
    }
}
