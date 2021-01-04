package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.*;
import progi.projekt.repository.BrojKrevetaRepository;
import progi.projekt.repository.OglasRepository;
import progi.projekt.repository.TipKupaoniceRepository;
import progi.projekt.service.UvjetiService;

import java.util.UUID;

@Service
public class UvjetiServiceImpl implements UvjetiService {
    public final OglasRepository oglasRepository;
    public final BrojKrevetaRepository brojKrevetaRepository;
    public final TipKupaoniceRepository tipKupaoniceRepository;

    public UvjetiServiceImpl(OglasRepository oglasRepository, BrojKrevetaRepository brojKrevetaRepository, TipKupaoniceRepository tipKupaoniceRepository){
        this.oglasRepository=oglasRepository;
        this.brojKrevetaRepository=brojKrevetaRepository;
        this.tipKupaoniceRepository=tipKupaoniceRepository;
    }

    @Override
    public  TrazeniUvjeti findByIdOglas(UUID id) {
        Oglas oglas = oglasRepository.findById(id);
        Student student = oglas.getStudent();
        TrazeniUvjeti trazeniUvjeti = student.getUvjeti();
        return trazeniUvjeti;
    }

    @Override
    public Integer izracunajBliskost(Soba soba, TrazeniUvjeti uvjeti) {
        return 0;
    }

    @Override
    public Boolean sobaMatchesUvjet(Soba soba, TrazeniUvjeti uvjeti) {
        BrojKreveta nebitno = brojKrevetaRepository.findByNaziv("NEBITNO");
        TipKupaonice neb = tipKupaoniceRepository.findByTip("NEBITNO");
        return (uvjeti.getBrojKreveta().contains(soba.getBrojKreveta()) || uvjeti.getBrojKreveta().contains(nebitno) || uvjeti.getBrojKreveta().isEmpty()
                && uvjeti.getTipKupaonice().contains(soba.getTipKupaonice()) || uvjeti.getTipKupaonice().contains(neb) || uvjeti.getTipKupaonice().isEmpty()
                && uvjeti.getDomovi().contains(soba.getPaviljon().getDom()) || uvjeti.getDomovi().isEmpty()
                && uvjeti.getGrad().equals(soba.getPaviljon().getDom().getGrad())
                && uvjeti.getKatovi().contains(soba.getKat()) || uvjeti.getKatovi().isEmpty());
    }

}
