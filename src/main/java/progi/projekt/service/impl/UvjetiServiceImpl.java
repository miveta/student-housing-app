package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Oglas;
import progi.projekt.model.Soba;
import progi.projekt.model.Student;
import progi.projekt.model.TrazeniUvjeti;
import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;
import progi.projekt.repository.OglasRepository;
import progi.projekt.service.UvjetiService;

import java.util.Optional;
import java.util.UUID;

@Service
public class UvjetiServiceImpl implements UvjetiService {
    public final OglasRepository oglasRepository;


    public UvjetiServiceImpl(OglasRepository oglasRepository) {
        this.oglasRepository = oglasRepository;
    }

    @Override
    public TrazeniUvjeti findByIdOglas(UUID id) {
        Optional<Oglas> oglas = oglasRepository.findById(id);
        if (oglas == null) return null;
        Student student = oglas.get().getStudent();
        TrazeniUvjeti trazeniUvjeti = student.getUvjeti();
        return trazeniUvjeti;
    }

    @Override
    public Integer izracunajBliskost(Soba soba, TrazeniUvjeti uvjeti) {
        return 0;
    }

    @Override
    public Boolean sobaMatchesUvjet(Soba soba, TrazeniUvjeti uvjeti) {
        BrojKrevetaEnum nebitno = BrojKrevetaEnum.NEBITNO;
        TipKupaoniceEnum neb = TipKupaoniceEnum.NEBITNO;
        return (uvjeti.getBrojKreveta().contains(soba.getBrojKreveta().name()) || uvjeti.getBrojKreveta().contains(nebitno) || uvjeti.getBrojKreveta().isEmpty()
                && uvjeti.getTipKupaonice().contains(soba.getTipKupaonice().name()) || uvjeti.getTipKupaonice().contains(neb) || uvjeti.getTipKupaonice().isEmpty()
                && uvjeti.getDomovi().contains(soba.getPaviljon().getDom()) || uvjeti.getDomovi().isEmpty()
                && uvjeti.getGrad().equals(soba.getPaviljon().getDom().getGrad())
                && uvjeti.getKatovi().contains(soba.getKat()) || uvjeti.getKatovi().isEmpty());
    }

}
