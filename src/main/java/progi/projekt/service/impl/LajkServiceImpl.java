package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Lajk;
import progi.projekt.repository.LajkRepository;
import progi.projekt.service.LajkService;

import java.util.List;

@Service
public class LajkServiceImpl implements LajkService {

    private LajkRepository lajkRepository;

    public LajkServiceImpl(LajkRepository lajkRepository) {
        this.lajkRepository = lajkRepository;
    }

    @Override
    public List<Lajk> listAll() {
        return lajkRepository.findAll();
    }
}
