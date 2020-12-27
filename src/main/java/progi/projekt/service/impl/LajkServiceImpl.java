package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Lajk;
import progi.projekt.model.LajkId;
import progi.projekt.repository.LajkRepository;
import progi.projekt.security.exception.SavingException;
import progi.projekt.service.LajkService;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Lajk> findLajk(LajkId lajkId) {
        try {
            return Optional.of(lajkRepository.findByLajkId(lajkId));
        } catch (Exception e) {
            //lajkRepo baca exceptione koje mu proslijedi baza (e)?
            String originalMessage = e.getMessage();
            //throw new LajkNotFoundException("No lajk with lajkId: '" + lajkId + "'");
            return Optional.empty();
        }
    }

    @Override
    public Lajk update(Lajk l) {
        try {
            return lajkRepository.saveAndFlush(l);
        } catch (Exception e) {
            //lajkRepo baca exceptione koje mu proslijedi baza (e)?
            throw new SavingException("Exception while saving lajk. Original message: '" + e.getMessage() + "'");
        }
    }
}
