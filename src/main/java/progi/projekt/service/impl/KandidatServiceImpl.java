package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Kandidat;
import progi.projekt.model.Oglas;
import progi.projekt.model.Soba;
import progi.projekt.model.TrazeniUvjeti;
import progi.projekt.repository.KandidatRepository;
import progi.projekt.service.*;

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
	private ParService parService;

	public KandidatServiceImpl(KandidatRepository kandidatRepo, SobaService sobaService, OglasService oglasService, UvjetiService uvjetiService, ParService parService) {
		this.kandidatRepo = kandidatRepo;
		this.sobaService = sobaService;
		this.oglasService = oglasService;
		this.uvjetiService = uvjetiService;
		this.parService = parService;
	}

	@Override
	public List<Kandidat> listAll(UUID oglasUuid) {
		//updateLocalKands(); //fun fact, ovo radi inf loop
		var oglas = oglasService.findById(oglasUuid.toString());
		return kandidatRepo.findAllByOglas(oglas.get());
	}

	@Override
	public List<Kandidat> listAll() {
		return kandidatRepo.findAll();
	}

	public void stvoriKand(Oglas oglas1, Oglas oglas2) {
//		System.out.print("oglas1 initial kandidati count: " + oglas1.getKandidati().stream().count() + "\n");
//		System.out.print("oglas2 initial kandidati count: " + oglas2.getKandidati().stream().count() + "\n");

		//force update kandidata unutar svakog oglasa
		updateLocalKands();

//		System.out.print("oglas1 update kandidati count: " + oglas1.getKandidati().stream().count() + "\n");
//		System.out.print("oglas2 update kandidati count: " + oglas2.getKandidati().stream().count() + "\n");

		Integer bliskost = calculateScore(oglas1, oglas2);
		java.sql.Date stvoren = new java.sql.Date(Calendar.getInstance().getTime().getTime());

		Kandidat kand = new Kandidat(oglas1, oglas2, bliskost, stvoren, false);

		if (!kandidatRepo.findAll().contains(kand)){
			save(kand);
		}

		oglas1.getKandidati().add(kand);
		oglasService.save(oglas1); //TODO: fix ovo, pa makniti updateLocalKands()

		oglas2.getKandidati().add(kand);
		oglasService.save(oglas2);


//		System.out.print("oglas1 kandidati count: " + oglas1.getKandidati().stream().count() + "\n");
//		System.out.print("oglas2 kandidati count: " + oglas2.getKandidati().stream().count() + "\n");


	}

	@Override
	public Boolean odgovaraju(Oglas oglas1, Oglas oglas2) {
		return true;
/*
		var uvjeti1 = oglas1.getStudent().getUvjeti();
		var uvjeti2 = oglas2.getStudent().getUvjeti();

		var soba1 = sobaService.getByStudentId(oglas1.getStudent().getId());
		var soba2 = sobaService.getByStudentId(oglas2.getStudent().getId());

        return (sobaMatchesUvjet(soba1.get(), uvjeti2) && sobaMatchesUvjet(soba2.get(), uvjeti1)) ? true : false;*/
	}

	@Override
	public int odgovaraIizTopN(Oglas oglas) {
		//ako nekom od topN kandidata ogovara nasa soba vrati topN indeks tog kandidata. Inace vrati -1

		updateLocalKands();

		List<Oglas> topN = topN(oglas);

		int i = 0;
		for (Oglas kand : topN) {
			if (odgovaraju(oglas, kand) && parService.josNisuPar(oglas, kand)) {
				return i;
			} else {
				i++;
			}
		}
		return -1;
	}

	@Override
	public List<Oglas> topN(Oglas oglas) {
		//primi oglas i vrati listu prvih N oglasa njegovih kandidata sortiranih po bliskosti pa datumu nastanka

		updateLocalKands();

		int N = SHORTLIST_VELICINA;
		//todo: staviti poruku korisnicima da moraju ocjeniti barem N oglasa

		Comparator<Kandidat> compareByBliskost = Comparator.comparing(Kandidat::getBliskost);
		Comparator<Kandidat> compareByStvoren = Comparator.comparing(Kandidat::getStvoren);
		Comparator<Kandidat> compareByBlskThenStvrn = compareByBliskost.thenComparing(compareByStvoren);

		List<Kandidat> top3kand = oglas.getKandidati()
				.stream()
				.filter(kand -> kand.getIgnore() != null && kand.getIgnore() != true)
                .filter(kand2 -> kand2.getOglas().getId() != oglas.getId())
				.sorted(compareByBlskThenStvrn)
				.limit(N)
				.collect(Collectors.toList());

		ArrayList<Oglas> top = new ArrayList<Oglas>();
		for (Kandidat kand : top3kand) {
			if (kand.getOglas() == oglas){
				top.add(kand.getKandOglas());
			} else {
				top.add(kand.getOglas());
			}
		}

		return top;
	}


	@Override
	public Boolean josNisuKandidat(Oglas oglas1, Oglas oglas2) {
		//vraca true ako dva oglasa nisu vec kandidat

		updateLocalKands();

		var kandidati1 = oglas1.getKandidati();
		var kandidati2 = oglas2.getKandidati();

		//return !kandidati1.contains(oglas2) && !kandidati2.contains(oglas1) ? true : false;

		for (Kandidat kand1 : kandidati1){
			//ako bilo koji od kandidata oglasa 1 sadrzi studenta oglasa 2, vec jesu kandidati
			var kandStud1 = kand1.getOglas().getStudent().getId();
			var kandStud2 = kand1.getKandOglas().getStudent().getId();
			var postojeciKandStud = oglas2.getStudent().getId();

			if (kandStud1 == postojeciKandStud || kandStud2 == postojeciKandStud){
				return false;
			}
		}
		return true;

	}

	@Override
	public void save(Kandidat kand) {
		kandidatRepo.save(kand);
	}

	@Override
	public Integer calculateScore(Oglas oglas1, Oglas oglas2) {
        Soba soba2 = sobaService.getByStudentUsername(oglas2.getStudent().getKorisnickoIme()).get();
		TrazeniUvjeti uvjeti1 = uvjetiService.findByIdOglas(oglas1.getId());
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

		updateLocalKands();

		for (Kandidat kand : oglas1.getKandidati()) {
			if (kandSadrziOglas(kand, oglas2)) {
				return Optional.ofNullable(kand);
			}
		}

		return kandidatOpt;
	}

	@Override
	/**
	 * Vraca True ako predani kandidat sadrzi predani oglas
	 */
	public Boolean kandSadrziOglas(Kandidat kand, Oglas oglas) {
		return kand.getOglas().equals(oglas) || kand.getKandOglas().equals(oglas);
	}

	@Override
	public void ponistiKandidateOglasa(Oglas oglas) {
		updateLocalKands();
		List<Kandidat> kandidati = listAll(oglas.getId());
		for (Kandidat kand : kandidati) {
			if (kandSadrziOglas(kand, oglas)){
				kand.setIgnore(true);
				save(kand);
			}
		}
	}

	@Override
	public void updateLocalKands() {
		List<Oglas> oglasi = oglasService.listAll();
		for (Oglas oglas : oglasi){
            List<Kandidat> kandidati = listAll(oglas.getId());
			for (Kandidat kandidat : kandidati){
				if (kandidat.getOglas().getId() == oglas.getId() || kandidat.getKandOglas().getId() == oglas.getId()) {
					if (!oglas.getKandidati().contains(kandidat)) {
						oglas.getKandidati().add(kandidat);
						oglasService.save(oglas);
					}
				}
			}
		}
	}

	public Boolean sobaMatchesUvjet(Soba soba, TrazeniUvjeti uvjeti) {
		updateLocalKands();
		return uvjetiService.sobaMatchesUvjet(soba, uvjeti);
	}
}
