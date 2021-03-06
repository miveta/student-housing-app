package progi.projekt.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import progi.projekt.model.*;
import progi.projekt.model.enums.StatusOglasaEnum;
import progi.projekt.service.*;
import progi.projekt.service.util.Key;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchingServiceImpl implements MatchingService {
	private final boolean NULL_DEBUG = false; //true samo za debug

	private final StudentService studentService;
	private final OglasService oglasService;
	private final KandidatService kandidatService;
	private final ParService parService;
	private final LajkService lajkService;

	public MatchingServiceImpl(
			StudentService studentService,
			OglasService oglasService,
			@Lazy KandidatService kandidatService,
			ParService parService,
			@Lazy LajkService lajkService) {
		this.studentService = studentService;
		this.oglasService = oglasService;
		this.kandidatService = kandidatService;
		this.parService = parService;
		this.lajkService = lajkService;
	}

	//ako ce opet javljat "The dependencies of some of the beans in the application context form a cycle"
	//pogledati https://www.baeldung.com/circular-dependencies-in-spring

	@Override
	public void kandidatiFun() {
		//prolazi po svim oglasima i stvara kandidat parove


		List<Oglas> oglasi = oglasService.listAll();

		//stvaranje novih kandidata
		for (Oglas oglas1 : oglasi) {
			for (Oglas oglas2 : oglasi) {
				if (oglas1 != oglas2 && ignoreNullNasloveDEBUG(oglas1, oglas2)) {
					if (kandidatService.odgovaraju(oglas1, oglas2) && kandidatService.josNisuKandidat(oglas1, oglas2)) {
						kandidatService.stvoriKand(oglas1, oglas2);
					}
				}
			}

		}

		//popunjavanje liste postojecih kandidata svakog oglasa iz liste svih kandidata
		kandidatService.updateLocalKands();
	}

	private boolean ignoreNullNasloveDEBUG(Oglas oglas1, Oglas oglas2) {
		if (NULL_DEBUG){
			if (oglas1.getNaslov() == null || oglas2.getNaslov() == null) return false;
			return true;
		}
		return true;
	}

	@Override
	public void resetKandsDebug(){
		List<Oglas> oglasi = oglasService.listAll();
		for (Oglas oglas : oglasi) {
			oglas.setKandidati(new ArrayList<Kandidat>());
		}
	}

	@Override
	public void parFun() {
		//prolazi po svim oglasima i stvara parove


		//force update kandidata unutar svakog oglasa
		kandidatService.updateLocalKands();

		List<Oglas> oglasi = oglasService.listAll();

		for (Oglas oglas1 : oglasi) {

			//force update kandidata unutar svakog oglasa
			kandidatService.updateLocalKands();

			//ako jedan od nasih topN kandidata takodjer ima nas oglas kao topN kandidat stvaramo par
			for (int j = 0; j<3; j++){
				int i = kandidatService.odgovaraIizTopN(oglas1);
				if (i != -1) {
					Oglas oglas2 = kandidatService.topN(oglas1).get(i);
					Par par = new Par(oglas1, oglas2);
					parService.save(par);
				}
			}


			//note: trenutno, broj parova koje moze imati jedan oglas je 3 jer
			// kandidatService.odgovaraIizTopN gleda top 3 oglasa sortirano po bliskosti i ne radi duplikate

			//ako nekom kandidatu naseg kandidata odgovaramo mi, imamo lanac
			Oglas oglasA = oglas1;
			List<Oglas> topNOglasA = kandidatService.topN(oglasA);
			for (Oglas oglasB : topNOglasA) {
				if (oglasB != oglasA && parService.josNisuLanac(oglasA, oglasB)) {
					List<Oglas> topNOglasB = kandidatService.topN(oglasB);
					for (Oglas oglasC : topNOglasB) {
						if (oglasC != oglasB && oglasC != oglasA && parService.josNisuLanac(oglasA, oglasC)) {
							if (kandidatService.odgovaraju(oglasA, oglasC)) {
								//kandidatService.stvoriKand(oglasA, oglasC);
								//kandidatService.stvoriKand(oglasB, oglasA);

								Par par1 = new Par(oglasA, oglasB, false, true, false);
								Par par2 = new Par(oglasB, oglasC, false, true, false);
								Par par3 = new Par(oglasC, oglasA, false, true, false);
								boolean s1 = parService.save(par1);
								if (s1) s1 = parService.save(par2);
								if (s1) s1 = parService.save(par3);
							}
						}
					}
				}
			}

		}
	}


	//student u medjuvremenu lajka kandidate (ili bilo koji oglas)


	@Override
	public void lajkFun() {
		//poziva se uz metodu iz LajkControllera. Stvara par ako je student ocjenio oglas koji mu inicijalno
		// nije ponudjen kao kandidat pa nije mogao biti u topN pa niti postati par


		//force update kandidata unutar svakog oglasa
		kandidatService.updateLocalKands();

		List<Lajk> lajkovi = lajkService.listAll();

		for (Lajk lajk : lajkovi){
			Student stud = lajk.getLajkId().getStudent();
			Oglas oglas1 = stud.getAktivniOglas();
			Oglas oglas2 = lajk.getLajkId().getOglas();

			if (lajk.getOcjena() > 0) {
				if (kandidatService.josNisuKandidat(oglas1, oglas2)) {
					kandidatService.stvoriKand(oglas1, oglas2);
				}

				if (parService.josNisuPar(oglas1, oglas2)) {
					Par par = new Par(oglas1, oglas2);
					parService.save(par);
				}
			} else if (lajk.getOcjena() == 0) {
				Optional<Kandidat> kandOpt = oglas1.getKandidati()
						.stream()
						.filter(kandidat ->
								kandidatService.kandSadrziOglas(kandidat, oglas1) &&
								kandidatService.kandSadrziOglas(kandidat, oglas2))
						.findFirst();
				kandOpt.ifPresent(kandidat -> ponistiKandidat(kandidat));

				Optional<Par> parOpt = parService.pripadniParDvaOglasa(oglas1, oglas2);
				parOpt.ifPresent(par -> ponistiPar(par));
			}




		}
	}


	@Override
	public void matchFun() {
		//iterira po parovima i ovisno o obostranoj ocjeni rezervira najbolje ocjenjeni

		//force update kandidata unutar svakog oglasa
		kandidatService.updateLocalKands();


		//kopiranje ocjene iz liste lajkova u rjecnik dvostrukog kljuca (id_oglas1, id_oglas2)
		Hashtable<Key, Integer> ocjene = new Hashtable<Key, Integer>();
		List<Oglas> oglasiUnsorted = oglasService.listAll();
		List<Oglas> oglasi = oglasiUnsorted
				.stream()
				.sorted(Comparator.comparing(Oglas::getObjavljen))
				.collect(Collectors.toList());

		/*
		spremanje ocjene u kandidat:
		klasa kandidat ne razlikuje "originalni" oglas i "kandidat" oglas usprkos imenima varijabli
		zbog toga se moramo interno dogovoriti kako pridruzujemo ocjene
		ja predlazem da ako npr. Kandidat kand ima oglasA (od studenta A) i oglasB (od studenta B)
		i oglasA je pospremljen kao oglas, a oglasB kao kandOglas onda ocjenu studenta B
		za oglas A pospremimo u kandidat.ocjena1, ocjenu studenta A za oglas B u ocjena2
			- holik
		*/


		/*
		//VERZIJA ZA IZVLACENJE OCJENA IZ KANDIDATA

		//izvlacenje ocjena iz kandidata u 2D dict [{oglasOcjenjivac, oglasOcjenjeni}] = ocjena
		//prvo iteriramo po oglasima a ne kandidatima kako bi polje ocjena bilo sortirano po vremenu stvaranja oglasa
		//tj tko prije stvori oglas taj ima vecu prednost u matchanju
		//zato je polje oglasi sortirano po datumu objave
		for (Oglas oglasA : oglasi) {
			List<Kandidat> kandidati = oglasA.getKandidati();
			for (Kandidat kand : kandidati) {
				Oglas oglasB;
				Optional<Integer> ocjenaOptional;
				if (kand.getOglas() == oglasA) {
					oglasB = kand.getKandOglas();
					ocjenaOptional = Optional.ofNullable(kand.getOcjena2());
				} else {
					oglasB = kand.getOglas();
					ocjenaOptional = Optional.ofNullable(kand.getOcjena1());
				}

				UUID id1 = oglasA.getId(); //oglas studenta koji je dao ocjenu
				UUID id2 = oglasB.getId(); //oglas koji je ocjenjen
				Key key = new Key(id1, id2);

				ocjenaOptional.ifPresentOrElse(
						(ocjena) ->
						{
							ocjene.put(key, ocjena);
						},
						() ->
						{
							//ako ocjena jos nije unesena u rjecnik upisujemo -1
							ocjene.put(key, -1);
						});
			}
		}
		*/

		//VERZIJA ZA IZVLACENJE OCJENA IZ LAJKA

		//iteriramo po oglasima a ne lajkovima kako bi polje ocjena bilo sortirano po vremenu stvaranja oglasa
		//tj tko prije stvori oglas taj ima vecu prednost u matchanju
		//zato je polje oglasi sortirano po datumu objave
		for (Oglas oglas1 : oglasi) {
			UUID studentID = oglas1.getStudent().getId();
			for (Oglas oglas2 : oglasi) {
				if (!oglas1.getId().equals(oglas2.getId())){
					Optional<Lajk> lajkOpt = lajkService.findLajkDvaOglasa(oglas1, oglas2);

					UUID id1 = oglas1.getId();
					UUID id2 = oglas2.getId();
					Key key = new Key(id1, id2);

					lajkOpt.ifPresentOrElse(
							(lajk) ->
							{
								Integer ocjena = lajk.getOcjena();
								ocjene.put(key, ocjena);
							},
							() ->
							{
								//ako ocjena jos nije unesena u rjecnik upisujemo -1
								ocjene.put(key, -1);
							});
				}


			}

		}


		//racuanje obostranih ocjena i pospremanje u rijecnik
		Hashtable<Par, Integer> obostraneOcjene = new Hashtable<Par, Integer>();
		List<Par> parovi = parService.listAll();
		for (Par par : parovi) {
			if (par.getLanac() == false) {
				//obican par
				Key key1 = new Key(par.getOglas1().getId(), par.getOglas2().getId());
				Key key2 = new Key(par.getOglas2().getId(), par.getOglas1().getId());
				Optional<Integer> ocjena1Optional = Optional.ofNullable(ocjene.get(key1));
				Optional<Integer> ocjena2Optional = Optional.ofNullable(ocjene.get(key2));

				Integer obostranaOcjena;
				//ako bilo koji od studenata nije ocjenio par
				if (ocjena1Optional.isEmpty() || ocjena2Optional.isEmpty()){
					obostranaOcjena = -1;
				} else {
					Integer ocjena1 = ocjena1Optional.get();
					Integer ocjena2 = ocjena2Optional.get();

					if (ocjena1 == 0 || ocjena2 == 0){
						obostranaOcjena = 0;
					}
					else {
						obostranaOcjena = parService.ObostranaOcjena(ocjena1, ocjena2);
					}
				}

				obostraneOcjene.put(par, obostranaOcjena);

			} else {
				//lanac

				List<Oglas> oglasi3 = parService.pronadjiTreciOglasIzLanca(par);

				for (Oglas oglas3 : oglasi3) {
					Key keyA;
					Key keyB;
					Key keyC;

					keyA = new Key(par.getOglas1().getId(), par.getOglas2().getId());
					keyB = new Key(par.getOglas2().getId(), oglas3.getId());
					keyC = new Key(oglas3.getId(), par.getOglas1().getId());

					Optional<Integer> ocjenaABOptional = Optional.ofNullable(ocjene.get(keyA));
					Optional<Integer> ocjenaBCOptional = Optional.ofNullable(ocjene.get(keyB));
					Optional<Integer> ocjenaCAOptional = Optional.ofNullable(ocjene.get(keyC));

					Integer trostranaOcjena;

					//ako bilo koji od studenata nije ocjenio par
					if (	ocjenaABOptional.isEmpty() || ocjenaABOptional.get().equals(-1) ||
							ocjenaBCOptional.isEmpty() || ocjenaBCOptional.get().equals(-1) ||
							ocjenaCAOptional.isEmpty() || ocjenaCAOptional.get().equals(-1))  {
						trostranaOcjena = -1;
					} else {
						Integer ocjenaAB = ocjenaABOptional.get();
						Integer ocjenaBC = ocjenaBCOptional.get();
						Integer ocjenaCA = ocjenaCAOptional.get();

						if (ocjenaAB == 0 || ocjenaBC == 0 || ocjenaCA == 0){
							trostranaOcjena = 0;
						}
						else {
							trostranaOcjena = parService.TrostranaOcjena(ocjenaAB, ocjenaBC, ocjenaCA);
						}

					}
					List<Par> lanacParovi = parService.pripadniParoviLanca(par.getOglas1());
					for (Par parLanca : lanacParovi) {
						obostraneOcjene.put(parLanca, trostranaOcjena);
					}

				}
			}
		}

		//stvaranje para koji se salje studentu na potvrdu i postavljanje statusa oba oglasa u CEKA
		for (Oglas oglasI : oglasi) {
			Optional<Par> konacniParOptional = parovi
					.stream()
					.filter(parSa -> parService.parSadrziOglas(parSa, oglasI))
					.filter(parIg -> parIg.getIgnore() != true) //par nije ponisten
					.filter(parAl -> parService.ifObaAKTIVAN(parAl)) //stanja oba oglasa == AKTIVAN
					.sorted(Comparator.comparing(parSr -> obostraneOcjene.getOrDefault(parSr, -1)))
					.filter(parVa -> obostraneOcjene.getOrDefault(parVa, -1) > 0) //ocjena > 0
					.findFirst();

			konacniParOptional.ifPresent(
					(konacniPar) ->
					{
						Par par = konacniPar;
						final boolean SKIP_IZRADU_PAROVA = false;
						if (par.getLanac() == false && !SKIP_IZRADU_PAROVA) {
							//nema lanca
							parService.rezervirajOglasePara(par);
						} else if (par.getLanac() == true) {
							//lanac
							List<Oglas> lanci = parService.pripadniOglasiLanca(par.getOglas1());
							for(int k = lanci.size()/3;k>0;k--){
								Optional<Par> par2Opt =
										parService.pripadniParAB(lanci.get(3*k-2), lanci.get(3*k-1));


								if (par2Opt.isPresent()) {
									Par par2 = par2Opt.get();
									parService.rezervirajOglasePara(par);
									parService.rezervirajOglasePara(par2);

								} else {
									//ovo se ne moze dogoditi jer se lanci uvijek rade skupa
									//tabs > spaces
									System.out.print("Neko je prcko po bazi i postavio flag lanac=true");
								}
							}

						}
					}
			);
		}

	}

	@Override
	public void confirmFun() {
		//Cita rezultat konacnih potvrda para i:
		//ako su oba studenta prihvatila (par.prihvatioPrvi/Drugi = true) oznacuje oglas.status=POTVRDEN
		//ako oba studenta nisu prihvatila (par.ignore = true): oznacuje kandidat i par sa ignore (vise
		// se ne spajaju/gledaju)

		//tj, ako je lanac=true onda gledamo 3 oglasa, ne samo 2

		//force update kandidata unutar svakog oglasa
		kandidatService.updateLocalKands();

		List<Par> parovi = parService.listAll();
		for (Par par : parovi) {

			//ako nemamo lanac
			if (par.getLanac() == false) {
				confirmFunLegwork(par);
			}
			//ako imamo lanac
			else {
				List<Par> lanacParovi = parService.pripadniParoviLanca(par.getOglas1());
				for (Par parLanca : lanacParovi) {
					confirmFunLegwork(parLanca);
				}
			}
		}
	}

	public void confirmFunLegwork(Par par) {
		//force update kandidata unutar svakog oglasa
		kandidatService.updateLocalKands();

		if (parService.ifObaCEKA(par)){
			//oba studenta su prihvatila par (ignore != false) i oglas nije u medjuvremenu ponisten (oba == CEKA)
			if (par.getIgnore() == false && parService.obaStudPrihvatila(par) == true && parService.ifObaCEKA(par)){
				par.setDone(true);
				parService.save(par);

				//oglas.status=POTVRDJEN
				parService.potvrdiOglasePara(par);

				//for kand in kandidati setIgnore(true)
				kandidatService.ponistiKandidateOglasa(par.getOglas1());
				kandidatService.ponistiKandidateOglasa(par.getOglas2());

				//for par in parovi if done!=true setIgnore(true)
				parService.ponistiParoveOglasa(par.getOglas1());
				parService.ponistiParoveOglasa(par.getOglas2());
			}

			//barem jedan od studenata nije prihvatio par
			else /* par.getIgnore() == true) */ {
				ponistiParIKandidat(par);
				parService.vratiOglaseParaNaAKTIVAN(par);
				parFun();
				matchFun();
			}
		} else {
			//jedan od oglasa je u medjuvremenu obrisan/ponisten
			ponistiParIKandidat(par);
		}
	}

	public void ponistiParIKandidat(Par par) {
		ponistiPar(par);

		//ponistavanje povezanog kandidata
		Optional<Kandidat> kandidatOptional = par.getOglas1().getKandidati()
				.stream()
				.filter(kand -> kand.getOglas().getId() == par.getOglas1().getId()
						&& kand.getKandOglas().getId() == par.getOglas2().getId())
				.findFirst();

		kandidatOptional.ifPresent(kandidat -> {
			ponistiKandidat(kandidat);
		});
	}

	public void ponistiKandidat(Kandidat kandidat) {
		kandidat.setIgnore(true);
		kandidatService.save(kandidat);
	}

	public void ponistiPar(Par par) {
		//ponistavanje odbijenog para
		par.setIgnore(true);
		parService.save(par);

		//ciscenje konacnog para iz oglasa
		Oglas oglas1 = par.getOglas1();
		Oglas oglas2 = par.getOglas2();

		if (oglas1.getKandidati() == par){
			oglas1.setKonacniPar(null);
			oglasService.save(oglas1);
		}

		if (oglas2.getKandidati() == par){
			oglas2.setKonacniPar(null);
			oglasService.save(oglas2);
		}

	}


	public void resetirajOglas(UUID oglasId) {
		Optional<Oglas> oglasOpt = oglasService.findById(String.valueOf(oglasId));

		if (oglasOpt.isPresent()){
			Oglas oglas = oglasOpt.get();
			oglas.setStatusOglasa(StatusOglasaEnum.AKTIVAN);

			kandidatService.ponistiKandidateOglasa(oglas);
			lajkService.ponistiLajkoveOglasa(oglas);
			parService.ponistiParoveOglasa(oglas);

			resetKandsDebug();

			oglas.setKonacniPar(null);

			oglasService.save(oglas);
		}
	}


	@Override
	public void confirmSCFun() {
		for (Par par : parService.listAll()) {
			if (par.getOdobren() != null) {
				var oglas1 = par.getOglas1();
				var oglas2 = par.getOglas2();
				if (par.getOdobren() == true) {
					oglas1.setStatusOglasa(StatusOglasaEnum.IZVEDEN);
					oglas2.setStatusOglasa(StatusOglasaEnum.IZVEDEN);
				} else {
					oglas1.setStatusOglasa(StatusOglasaEnum.ODBIJEN);
					oglas2.setStatusOglasa(StatusOglasaEnum.ODBIJEN);
				}
			}
		}
	}
}
