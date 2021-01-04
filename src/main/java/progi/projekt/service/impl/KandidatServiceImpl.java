package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.projekt.model.*;
import progi.projekt.repository.KandidatRepository;
import progi.projekt.service.KandidatService;
import progi.projekt.service.UvjetiService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class KandidatServiceImpl implements KandidatService {
	private static final int SHORTLIST_VELICINA = 3;
	//veci broj -> brzi i blazi algoritam

	@Autowired
	private KandidatRepository kandidatRepo;
    //TODO: maknuto zbog UUID
	@Override
	public List<Kandidat> listAll(UUID oglasUuid) {
		//return kandidatRepo.findAllByIdOglas(oglasUuid);
		return new ArrayList<Kandidat>();
	}

	@Override
	public Boolean odgovaraju(Oglas oglas1, Oglas oglas2) {

		var uvjeti1 = oglas1.getStudent().getUvjeti();
		var uvjeti2 = oglas2.getStudent().getUvjeti();

		var soba1 = oglas1.getStudent().getSoba();
		var soba2 = oglas2.getStudent().getSoba();

		return (sobaMatchesUvjet(soba1, uvjeti2) && sobaMatchesUvjet(soba2, uvjeti1)) ? true : false;
	}

	@Override
	public int odgovaraIizTopN(Oglas oglas) {
		//ako nekom od topN kandidata ogovara nasa soba vrati indeks tog kandidata. Inace vrati -1

		List<Oglas> topN = topN(oglas);

		int i = 0;
		for (Oglas kand : topN){
			if (odgovaraju(oglas, kand)){
				return i;
			}
			else {
				i++;
			}
		}
		return -1;
	}

	@Override
	public List<Oglas> topN(Oglas oglas) {
		//primi oglas i vrati listu prvih N Oglasa kandidata sortirano po bliskosti

		int N = SHORTLIST_VELICINA;

		List<Kandidat> top3kand = oglas.getKandidati()
				.stream()
				.sorted(Comparator.comparing(Kandidat::getBliskost))
				.limit(N)
				.collect(Collectors.toList());

		List<Oglas> top = Collections.emptyList();
		for (Kandidat kand : top3kand) {
			top.add(kand.getOglas());
		}

		return top;
	}


	@Override
	public Boolean josNisuKandidat(Oglas oglas1, Oglas oglas2) {
		//vraca true ako dva oglasa nisu vec kandidat

		var kandidati1 = oglas1.getKandidati();
		var kandidati2 = oglas2.getKandidati();

		return !kandidati1.contains(oglas2) && !kandidati2.contains(oglas1) ? true : false;

	}

	@Override
	public void save(Kandidat kand) {
		kandidatRepo.save(kand);
	}

	@Override
	public Integer calculateScore(Oglas oglas1, Oglas oglas2) {
		Soba soba2 = oglas2.getStudent().getSoba();
		TrazeniUvjeti uvjeti1 = UvjetiService.findByIdOglas(oglas1.getId_oglas());

		Integer bliskost = UvjetiService.izracunajBliskost(soba2, uvjeti1);

		return bliskost;
	}

	public Boolean sobaMatchesUvjet (Soba soba, TrazeniUvjeti uvjeti){
		return UvjetiService.sobaMatchesUvjet(soba, uvjeti);
	}
}
