package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Lajk;
import progi.projekt.model.LajkId;
import progi.projekt.model.Oglas;
import progi.projekt.model.Student;
import progi.projekt.repository.LajkRepository;
import progi.projekt.security.exception.SavingException;
import progi.projekt.service.LajkService;

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
            Optional<Lajk> lajk = lajkRepository.findById(lajkId);
            return lajk;
        } catch (Exception e) {
            //lajkRepo baca exceptione koje mu proslijedi baza (e)?
            String originalMessage = e.getMessage();
            //throw new LajkNotFoundException("No lajk with lajkId: '" + lajkId + "'");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Lajk> findLajk(Student student, Oglas oglas) {
        LajkId lajkId = new LajkId(student, oglas);
        return findLajk(lajkId);
    }

    @Override
    public Optional<Lajk> findLajkDvaOglasa(Oglas oglas1, Oglas oglas2) {
        List<Lajk> lajkovi = listAll();

        for (Lajk lajkTmp : lajkovi){
            Oglas oglasTmp1 = lajkTmp.getLajkId().getOglas();
            Oglas oglasTmp2 = lajkTmp.getLajkId().getStudent().getOglas();
            if (oglasTmp1 == oglas1 && oglasTmp2 == oglas2) return Optional.of(lajkTmp);
            if (oglasTmp1 == oglas2 && oglasTmp2 == oglas1) return Optional.of(lajkTmp);
        }

        return Optional.empty();
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

    @Override
    public Lajk delete(Lajk lajk) throws SavingException {
        lajkRepository.delete(lajk);
        return null;
    }

    @Override
    public void save(Lajk lajk) {
        lajkRepository.save(lajk);
    }
}
