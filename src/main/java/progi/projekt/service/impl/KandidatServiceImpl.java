package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Kandidat;
import progi.projekt.model.Oglas;
import progi.projekt.model.Soba;
import progi.projekt.model.TrazeniUvjeti;
import progi.projekt.repository.KandidatRepository;
import progi.projekt.service.KandidatService;
import progi.projekt.service.OglasService;
import progi.projekt.service.SobaService;
import progi.projekt.service.UvjetiService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class KandidatServiceImpl implements KandidatService {
	private static final int SHORTLIST_VELICINA = 3;
	//veci broj -> brzi i blazi algoritam


	private KandidatRepository kandidatRepo;
	private SobaService sobaService;
	private OglasService oglasService;
	private UvjetiService uvjetiService;

	public KandidatServiceImpl(KandidatRepository kandidatRepo, SobaService sobaService, OglasService oglasService, UvjetiService uvjetiService) {
		this.kandidatRepo = kandidatRepo;
		this.sobaService = sobaService;
		this.oglasService = oglasService;
		this.uvjetiService = uvjetiService;
	}

    //TODO: maknuto zbog UUID
	/**/
	@Override
	public List<Kandidat> listAll(UUID oglasUuid) {
		var oglas = oglasService.findById(oglasUuid.toString());
		return kandidatRepo.findAllByOglas(oglas.get());
		//return new ArrayList<Kandidat>();
	}

	@Override
	public Boolean odgovaraju(Oglas oglas1, Oglas oglas2) {

		var uvjeti1 = oglas1.getStudent().getUvjeti();
		var uvjeti2 = oglas2.getStudent().getUvjeti();

		var soba1 = sobaService.getByStudentId(oglas1.getStudent().getId());
		var soba2 = sobaService.getByStudentId(oglas2.getStudent().getId());

		return (sobaMatchesUvjet(soba1, uvjeti2) && sobaMatchesUvjet(soba2, uvjeti1)) ? true : false;
	}

	@Override
	public int odgovaraIizTopN(Oglas oglas) {
		//ako nekom od topN kandidata ogovara nasa soba vrati indeks tog kandidata. Inace vrati -1

		List<Oglas> topN = topN(oglas);

		int i = 0;
		for (Oglas kand : topN) {
			if (odgovaraju(oglas, kand)) {
				return i;
			} else {
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
		Soba soba2 = sobaService.getByStudentId(oglas2.getStudent().getId());
		TrazeniUvjeti uvjeti1 = uvjetiService.findByOglas(oglas1);
		/*Soba soba2 = oglas2.getStudent().getSoba();
		TrazeniUvjeti uvjeti1 = uvjetiService.findByIdOglas(oglas1.getId());*/

		Integer bliskost = uvjetiService.izracunajBliskost(soba2, uvjeti1);

		return bliskost;
	}

	public Boolean sobaMatchesUvjet(Soba soba, TrazeniUvjeti uvjeti) {
		return uvjetiService.sobaMatchesUvjet(soba, uvjeti);
	}
}
