package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.projekt.model.Grad;
import progi.projekt.repository.GradRepository;
import progi.projekt.service.SobaService;

import java.util.List;

@Service
public class SobaServiceImpl implements SobaService {
    @Autowired
    GradRepository gradRepository;

    public List<Grad> findAllGrad() {
        return gradRepository.findAll();
    }
}
