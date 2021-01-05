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

import java.util.*;
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

	public void stvoriKand(Oglas oglas1, Oglas oglas2) {
		Integer bliskost = calculateScore(oglas1, oglas2);
		java.sql.Date stvoren = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		Kandidat kand = new Kandidat(oglas1, oglas2, bliskost, stvoren, false);

		oglas1.getKandidati().add(kand); //je li ovo persista? je li potrebno?
		oglas2.getKandidati().add(kand);

		save(kand);
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
		//ako nekom od topN kandidata ogovara nasa soba vrati topN indeks tog kandidata. Inace vrati -1

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
		//todo: staviti poruku korisnicima da moraju ocjeniti barem N oglasa

		//todo: ukljuciti provjeru flagova prilikom izrade topN
		//mislim da je to dosta, tj da od tada sve kandidate/paraove
		//mozemo tretirati kao clean

		Comparator<Kandidat> compareByBliskost = Comparator.comparing(Kandidat::getBliskost);
		Comparator<Kandidat> compareByStvoren = Comparator.comparing(Kandidat::getStvoren);
		Comparator<Kandidat> compareByBlskThenStvrn = compareByBliskost.thenComparing(compareByStvoren);

		List<Kandidat> top3kand = oglas.getKandidati()
				.stream()
				.sorted(compareByBlskThenStvrn)
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

	@Override
	/**
	 * Vraca Kandidata koji sadrzi predane oglase
	 */
	public Optional<Kandidat> kandidatParaOglasa(Oglas oglas1, Oglas oglas2) {
		Optional<Kandidat> kandidatOpt = Optional.empty();

		for (Kandidat kand : listAll(oglas1.getId())){
			if (kandSadrziOglas(kand, oglas2)){
				kandidatOpt = Optional.ofNullable(kand);
				break;
			}
		}

		return kandidatOpt;
	}

	@Override
	/**
	 * Vraca True ako predani kandidat sadrzi predani oglas
	 */
	public Boolean kandSadrziOglas(Kandidat kand, Oglas oglas) {
		return kand.getOglas() == oglas || kand.getKandOglas() == oglas;
	}

	public Boolean sobaMatchesUvjet(Soba soba, TrazeniUvjeti uvjeti) {
		return uvjetiService.sobaMatchesUvjet(soba, uvjeti);
	}
}
