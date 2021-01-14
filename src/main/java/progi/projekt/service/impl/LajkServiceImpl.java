package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.*;
import progi.projekt.repository.LajkRepository;
import progi.projekt.security.exception.SavingException;
import progi.projekt.service.LajkService;
import progi.projekt.service.MatchingService;
import progi.projekt.service.ObavijestService;
import progi.projekt.service.ParService;
import progi.projekt.service.ObavijestService;

import java.util.List;
import java.util.Optional;

@Service
public class LajkServiceImpl implements LajkService {

    private LajkRepository lajkRepository;
    private MatchingService matchingService;
    private ParService parService;
    private ObavijestService obavijestService;
    private ObavijestService obavijestService;

    public LajkServiceImpl(LajkRepository lajkRepository) {
        this.lajkRepository = lajkRepository;
        this.matchingService = matchingService;
        this.parService = parService;
        this.obavijestService = obavijestService;
    }

    @Override
    public List<Lajk> listAll() {
        return lajkRepository.findAll();
    }

    @Override
    public Optional<Lajk> findLajk(LajkId lajkId) {
        try {
            return lajkRepository.findById(lajkId);
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
            Oglas oglasTmp2 = lajkTmp.getLajkId().getStudent().getAktivniOglas();
            if (oglasTmp1 == oglas1 && oglasTmp2 == oglas2) return Optional.of(lajkTmp);
            if (oglasTmp1 == oglas2 && oglasTmp2 == oglas1) return Optional.of(lajkTmp);
        }

        return Optional.empty();
    }

    @Override
    public Lajk update(Lajk l) {
        try {
            Oglas oglas1 = l.getLajkId().getOglas();
            Oglas oglas2 = l.getLajkId().getStudent().getAktivniOglas();
            Optional<Par> pripradniPar = parService.pripadniParDvaOglasa(oglas1, oglas2);
            pripradniPar.ifPresent(par -> matchingService.ponistiPar(par));

            if(l.getOcjena() != 4)
                obavijestService.notifyLiked(l.getLajkId().getOglas(), l.getLajkId().getStudent());
            return lajkRepository.saveAndFlush(l);
        } catch (Exception e) {
            //lajkRepo baca exceptione koje mu proslijedi baza (e)?
            throw new SavingException("Exception while saving lajk. Original message: '" + e.getMessage() + "'");
        }
    }

    @Override
    public Lajk delete(Lajk lajk) throws SavingException {

        Oglas oglas1 = lajk.getLajkId().getOglas();
        Oglas oglas2 = lajk.getLajkId().getStudent().getAktivniOglas();
        Optional<Par> pripradniPar = parService.pripadniParDvaOglasa(oglas1, oglas2);
        pripradniPar.ifPresent(par -> matchingService.ponistiPar(par));

        lajkRepository.delete(lajk);
        return null;
    }

    @Override
    public void save(Lajk lajk) {
        lajkRepository.save(lajk);
    }

    @Override
    public void ponistiLajkoveOglasa(Oglas oglas) {
        List<Lajk> lajkovi = listAll();

        for (Lajk lajk : lajkovi){
            if (lajk.getLajkId().getStudent().equals(oglas.getStudent())){
                delete(lajk);
            }
        }
    }
}
