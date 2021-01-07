package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Oglas;
import progi.projekt.model.Soba;
import progi.projekt.model.TrazeniUvjeti;
import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;
import progi.projekt.repository.OglasRepository;
import progi.projekt.service.UvjetiService;

import java.util.Optional;
import java.util.UUID;

@Service
public class UvjetiServiceImpl implements UvjetiService {
    private final OglasRepository oglasRepository;


    public UvjetiServiceImpl(OglasRepository oglasRepository) {
        this.oglasRepository = oglasRepository;
    }

    @Override
    public TrazeniUvjeti findByIdOglas(UUID id) {
        Optional<Oglas> optionalOglas = oglasRepository.findById(id);
        if (optionalOglas.isEmpty()) return null;
        return optionalOglas.get().getTrazeniUvjeti();
    }

    @Override
    public Integer izracunajBliskost(Soba soba, TrazeniUvjeti uvjeti) {
        int rezultat = 0;
        if(soba.getStudent().getGrad().equals(uvjeti.getGrad())) rezultat++;
        if(uvjeti.getDomovi().contains(soba.getPaviljon().getDom()) || uvjeti.getDomovi().isEmpty()) rezultat++;
        if(uvjeti.getPaviljoni().contains(soba.getPaviljon()) || uvjeti.getPaviljoni().isEmpty()) rezultat++;
        if(uvjeti.getKatovi().contains(soba.getKat()) || uvjeti.getKatovi().isEmpty()) rezultat++;
        if(uvjeti.getBrojKreveta().contains(soba.getBrojKreveta()) || uvjeti.getBrojKreveta().isEmpty() || uvjeti.getBrojKreveta().contains(BrojKrevetaEnum.NEBITNO)) rezultat++;
        if(uvjeti.getTipKupaonice().contains(soba.getTipKupaonice()) || uvjeti.getTipKupaonice().isEmpty() || uvjeti.getTipKupaonice().contains(TipKupaoniceEnum.NEBITNO)) rezultat++;
        return rezultat;
    }

    @Override
    public Boolean sobaMatchesUvjet(Soba soba, TrazeniUvjeti uvjeti) {
        return true;
    }

}
