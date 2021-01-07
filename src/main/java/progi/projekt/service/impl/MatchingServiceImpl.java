package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.Kandidat;
import progi.projekt.model.Lajk;
import progi.projekt.model.Oglas;
import progi.projekt.model.Par;
import progi.projekt.model.enums.StatusOglasaEnum;
import progi.projekt.service.*;
import progi.projekt.service.util.Key;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchingServiceImpl implements MatchingService {
	private final StudentService studentService;
	private final OglasService oglasService;
	private final KandidatService kandidatService;
	private final ParService parService;
	private final LajkService lajkService;

	public MatchingServiceImpl(
			StudentService studentService,
			OglasService oglasService,
			KandidatService kandidatService,
			ParService parService,
			LajkService lajkService)
	{
		this.studentService = studentService;
		this.oglasService = oglasService;
		this.kandidatService = kandidatService;
		this.parService = parService;
		this.lajkService = lajkService;
	}





	@Override
	public void kandidatiFun() {
		//prolazi po svim oglasima i stvara kandidat parove

		List<Oglas> oglasi = oglasService.listAll();

		//stvaranje novih kandidata
		for (Oglas oglas1 : oglasi){
			for (Oglas oglas2 : oglasi){
				//todo: maknuti naslov == null kada se poprave oglasi u database fillu
				if (oglas1 != oglas2 && (oglas1.getNaslov() == null) && (oglas2.getNaslov() == null)){
					if (kandidatService.odgovaraju(oglas1, oglas2) && kandidatService.josNisuKandidat(oglas1, oglas2)){
						kandidatService.stvoriKand(oglas1, oglas2);
					}
				}

			}
		}

		//popunjavanje liste postojecih kandidata svakog oglasa iz liste svih kandidata
		for (Oglas oglas : oglasi){
			List<Kandidat> kandidati = kandidatService.listAll(oglas.getId());
			for (Kandidat kandidat : kandidati){
				if (kandidat.getOglas().getId() == oglas.getId() || kandidat.getKandOglas().getId() == oglas.getId()) {
					if (!kandidati.contains(kandidat)) {
						oglas.getKandidati().add(kandidat);
						oglasService.save(oglas);
					}
				}
			}
		}
	}

	//student u medjuvremenu lajka kandidate (ili bilo koji oglas)

	@Override
	public void lajkFun() {
		//poziva se uz metodu iz LajkControllera. Stvara kandidata/par ako je student ocjenio oglas koji mu inicijalno
		// nije ponudjen kao par pa nije mogao biti u topN pa niti postati par

		List<Lajk> lajkovi = lajkService.listAll();

		for (Lajk lajk : lajkovi){
			Oglas oglas1 = lajk.getLajkId().getStudent().getOglas();
			Oglas oglas2 = lajk.getLajkId().getOglas();

			if (kandidatService.josNisuKandidat(oglas1, oglas2)){
				kandidatService.stvoriKand(oglas1, oglas2);
			}
		}
	}


	@Override
	public void parFun() {
		//prolazi po svim oglasima i stvara parove

		List<Oglas> oglasi = oglasService.listAll();

		for (Oglas oglas1 : oglasi){
			//ako jedan od nasih topN kandidata takodjer ima nas oglas kao topN kandidat stvaramo par
			int i = kandidatService.odgovaraIizTopN(oglas1);
			if (i != -1){
				Oglas oglas2 = kandidatService.topN(oglas1).get(i);
				Par par = new Par (oglas1, oglas2);
				parService.save(par);
			}
			//ako nekom kandidatu naseg kandidata odgovaramo mi, imamo lanac
			else {
				Oglas oglasA = oglas1;
				List<Oglas> topNOglasA = kandidatService.topN(oglasA);
				for (Oglas oglasB : topNOglasA){
					List<Oglas> topNOglasB = kandidatService.topN(oglasB);
					for (Oglas oglasC : topNOglasB){
						if (kandidatService.odgovaraju(oglasA, oglasC)){
							kandidatService.stvoriKand(oglasA, oglasC);
							kandidatService.stvoriKand(oglasB, oglasA);

							Par par1 = new Par (oglasA, oglasB, false, true, false);
							Par par2 = new Par (oglasB, oglasC, false, true, false);
							Par par3 = new Par (oglasC, oglasA, false, true, false);
							parService.save(par1);
							parService.save(par2);
							parService.save(par3);
						}
					}
				}
			}
		}

	}

	@Override
	public void matchFun() {
		//po tablici 'par' i ovisno o obostranoj ocjeni para "rezervira" najbolje ocjenjeni par


		//kopiranje ocjene iz liste lajkova u rjecnik dvostrukog kljuca (id_oglas1, id_oglas2)
		Hashtable<Key, Integer> ocjene = new Hashtable<Key, Integer>();
		List<Oglas> oglasiUnsorted = oglasService.listAll();
		List<Oglas> oglasi = oglasiUnsorted
				.stream()
				.sorted(Comparator.comparing(Oglas::getObjavljen))
				.collect(Collectors.toList());

		/*
		//todo: prilagodi update controller/service
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
		for (Oglas oglas : oglasi) {
			UUID studentID = oglas.getStudent().getId();
			List<Lajk> lajkovi = lajkService.listAll();
			for (Lajk lajk : lajkovi) {
				if (lajk.getLajkId().getStudent().getId() == studentID) {
					UUID id1 = oglas.getId();
					UUID id2 = lajk.getLajkId().getOglas().getId();
					Key key = new Key(id1, id2);

					Optional<Integer> ocjenaOptional = Optional.ofNullable(lajk.getOcjena());

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
		}


		//racuanje obostranih ocjena i pospremanje u rijecnik
		Hashtable<Par, Integer> obostraneOcjene = new Hashtable<Par, Integer>();
		List<Par> parovi = parService.listAll();
		for (Par par : parovi){
			if (par.getLanac() == false){
				//obican par
				Key key1 = new Key(par.getOglas1().getId(), par.getOglas2().getId());
				Key key2 = new Key(par.getOglas2().getId(), par.getOglas1().getId());
				Optional<Integer> ocjena1Optional = Optional.ofNullable(ocjene.get(key1));
				Optional<Integer> ocjena2Optional = Optional.ofNullable(ocjene.get(key2));

				Integer obostranaOcjena;
				//ako bilo koji od studenata nije ocjenio par
				if (ocjena1Optional.isEmpty() ||ocjena2Optional.isEmpty()){
					obostranaOcjena = -1;
				}
				else{
					Integer ocjena1 = ocjena1Optional.get();
					Integer ocjena2 = ocjena2Optional.get();
					obostranaOcjena = parService.ObostranaOcjena(ocjena1, ocjena2);
				}
				obostraneOcjene.put(par, obostranaOcjena);

			} else {
				//lanac

				Optional<Oglas> oglas3Opt = parService.pronadjiTreciOglasIzLanca(par);

				if (oglas3Opt.isPresent()){
					Key keyA;
					Key keyB;
					Key keyC;

					Oglas oglas3 = oglas3Opt.get();

					keyA = new Key(par.getOglas1().getId(), par.getOglas2().getId());
					keyB = new Key(par.getOglas2().getId(), oglas3.getId());
					keyC = new Key(oglas3.getId(), par.getOglas1().getId());

					Optional<Integer> ocjenaABOptional = Optional.ofNullable(ocjene.get(keyA));
					Optional<Integer> ocjenaBCOptional = Optional.ofNullable(ocjene.get(keyB));
					Optional<Integer> ocjenaCAOptional = Optional.ofNullable(ocjene.get(keyC));

					Integer trostranaOcjena;

					//ako bilo koji od studenata nije ocjenio par
					if (	ocjenaABOptional.isEmpty() ||
							ocjenaBCOptional.isEmpty() ||
							ocjenaCAOptional.isEmpty()){
						trostranaOcjena = -1;
					}
					else{
						Integer ocjenaAB = ocjenaABOptional.get();
						Integer ocjenaBC = ocjenaBCOptional.get();
						Integer ocjenaCA = ocjenaCAOptional.get();

						trostranaOcjena = parService.TrostranaOcjena(ocjenaAB, ocjenaBC, ocjenaCA);
					}
					List<Par> lanacParovi = parService.pripadniParoviLanca(par.getOglas1());
					for (Par parLanca : lanacParovi){
						obostraneOcjene.put(parLanca, trostranaOcjena);
					}

				} else {
					//ovo se ne moze dogoditi jer se lanci uvijek rade skupa
					//pepsi > coke
					System.out.print("Neko je prcko po bazi i postavio flag lanac=true");
				}
			}
		}

		//stvaranje para koji se salje studentu na potvrdu i postavljanje statusa oba oglasa u CEKA
		for (Oglas oglasI : oglasi){
			Optional<Par> konacniParOptional = parovi
					.stream()
					.filter(parSa -> parService.parSadrziOglas(parSa, oglasI))
					.filter(parAl -> parService.ifObaAKTIVAN(parAl)) //t ako su stanja oba oglasa == AKTIVAN
					.sorted(Comparator.comparing(parSr -> obostraneOcjene.get(parSr)))
					.findFirst();

			konacniParOptional.ifPresent(
					(konacniPar) ->
					{
						//Par par = parovi.get(konacniPar.getIdPar()); //get radi sa indeksom da doi
						Par par = konacniPar;
						if (par.getLanac() == false){
							//nema lanca
							//parService.save(par);
							parService.rezervirajOglasePara(par);
						}
						else {
							//lanac
							Optional<Oglas> oglas3Opt = parService.pronadjiTreciOglasIzLanca(par);

							if (oglas3Opt.isPresent()){
								Par par2 = parService.pripadniParOglasa(oglas3Opt.get()).get();
								parService.rezervirajOglasePara(par);
								parService.rezervirajOglasePara(par2);

							} else {
								//ovo se ne moze dogoditi jer se lanci uvijek rade skupa
								//tabs > spaces
								System.out.print("Neko je prcko po bazi i postavio flag lanac=true");
							}
						}
					}
			);
		}



	}

	@Override
	public void confirmFun() {
		//Cita rezultat konacnih potvrda para i:
		//ako su oba studenta prihvatila: oznacuje par.done = true, oglas.status=POTVRDEN i salje mail u SC
		//ako oba studenta nisu prihvatila: oznacuje oba entiteta kandidat i par sa ignore (vise se ne spajaju/gledaju)
		//i vraca oglas nazad na AKTIVAN

		//tj, ako je lanac=true onda gledamo 3 oglasa, ne samo 2

		List<Par> parovi = parService.listAll();
		for (Par par : parovi){

			//ako nemamo lanac
			if (par.getLanac() == false){
				confirmFunLegwork(par);
			}
			//ako imamo lanac
			else {
				List<Par> lanacParovi = parService.pripadniParoviLanca(par.getOglas1());
				for (Par parLanca : lanacParovi){
					confirmFunLegwork(parLanca);
				}
			}
		}
	}

	public void confirmFunLegwork(Par par) {
		if (parService.ifObaCEKA(par)){
			//oba studenta su prihvatila par (ignore != false) i oglas nije u medjuvremenu zauzet (oba == AKTIVAN)
			if (par.getIgnore() == false){
				par.setDone(true);
				parService.save(par);
				parService.potvrdiOglasePara(par);
				kandidatService.ponistiKandidateOglasa(par.getOglas1());
				kandidatService.ponistiKandidateOglasa(par.getOglas2());
				parService.ponistiParoveOglasa(par.getOglas1());
				parService.ponistiParoveOglasa(par.getOglas2());
				//MailService.PotvrdaZamjenePara(par);
			}

			//barem jedan od studenata nije prihvatio par
			//todo za obavijestiService: odbijanjem para treba odraditi par.setIgnore(true)
			else /* par.getIgnore() == true) */{
				ponistiParIKandidat(par);
				parService.vratiOglaseParaNaAKTIVAN(par);
			}
		}
		else {
			//jedan od oglasa je u medjuvremenu obrisan/ponisten
			ponistiParIKandidat(par);
		}
	}

	public void ponistiParIKandidat(Par par) {
		par.setIgnore(true);
		parService.save(par);

		Optional<Kandidat> kandidatOptional = par.getOglas1().getKandidati()
				.stream()
				.filter(kand -> kand.getIdOglas() == par.getOglas1().getId()
						&& kand.getKandOglas().getId() == par.getOglas2().getId())
				.findFirst();

		kandidatOptional.ifPresent(kandidat -> {
			kandidat.setIgnore(true);
			kandidatService.save(kandidat);
		});
	}


	@Override
	public void confirmSCFun(List<Par> izvedeni) {
		for (Par par : izvedeni) {
			var oglas1 = par.getOglas1();
            oglas1.setStatusOglasa(StatusOglasaEnum.IZVEDEN);

			var oglas2 = par.getOglas2();
            oglas2.setStatusOglasa(StatusOglasaEnum.IZVEDEN);
		}
	}

	@Override
	public void confirmSCFun() {
		for (Par par : parService.listAll()) {
			var oglas1 = par.getOglas1();
            oglas1.setStatusOglasa(StatusOglasaEnum.IZVEDEN);

			var oglas2 = par.getOglas2();
            oglas2.setStatusOglasa(StatusOglasaEnum.IZVEDEN);
		}
	}
}
