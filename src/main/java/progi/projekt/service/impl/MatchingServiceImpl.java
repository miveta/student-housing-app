package progi.projekt.service.impl;

import org.springframework.stereotype.Service;
import progi.projekt.model.*;
import progi.projekt.model.enums.*;
import progi.projekt.service.*;
import progi.projekt.service.util.*;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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


	public void stvoriKand(Oglas oglas1, Oglas oglas2) {
		Integer bliskost = kandidatService.calculateScore(oglas1, oglas2);
		Kandidat kand = new Kandidat(oglas1, oglas2, bliskost, false);

		oglas1.getKandidati().add(kand); //je li ovo persista? je li potrebno?
		oglas2.getKandidati().add(kand);

		kandidatService.save(kand);
	}


	@Override
	public void kandidatiFun() {
		//prolazi po svim oglasima i stvara kandidat parove


		//popunjavanje liste postojecih kandidata svakog oglasa
		//ili ce to Spring napraviti sam radi @OneToMany(fetch = FetchType.LAZY, mappedBy = "kandidati") u Oglas.java?
		List<Oglas> oglasi = oglasService.listAll();

		for (Oglas oglas : oglasi){
			List<Kandidat> kandidati = kandidatService.listAll(oglas.getId_oglas());
			for (Kandidat kandidat : kandidati){
				if (kandidat.getOglas().getId_oglas() == oglas.getId_oglas() || kandidat.getIdKandidat() == oglas.getId_oglas()) {
					oglas.getKandidati().add(kandidat);
				}
			}
		}

		//dodavanje novih kandidata
		for (Oglas oglas1 : oglasi){
			for (Oglas oglas2 : oglasi){
				if (kandidatService.odgovaraju(oglas1, oglas2) && kandidatService.josNisuKandidat(oglas1, oglas2)){
					stvoriKand(oglas1, oglas2);
				}
			}
		}
	}



	@Override
	public void lajkFun() {
		//poziva se uz metodu iz LajkControllera. Stvara kandidata/par ako je student ocjenio oglas koji mu inicijalno
		// nije ponudjen kao par pa nije mogao biti u topN pa niti postati par

		List<Lajk> lajkovi = lajkService.listAll();

		for (Lajk lajk : lajkovi){
			Oglas oglas1 = lajk.getLajkId().getStudent().getOglas();
			Oglas oglas2 = lajk.getLajkId().getOglas();

			if (kandidatService.josNisuKandidat(oglas1, oglas2)){
				stvoriKand(oglas1, oglas2);
			}
		}
	}

	@Override
	public void parFun() {
		//prolazi po svim oglasima i puni tablicu 'par'

		List<Oglas> oglasi = oglasService.listAll();

		for (Oglas oglas1 : oglasi){
			int i = kandidatService.odgovaraIizTopN(oglas1);

			//ako jedan od nasih topN kandidata takodjer ima nas oglas kao topN kandidat stvaramo par
			if (i != -1){
				Oglas oglas2 = oglas1.getKandidati().get(i).getOglas();
				Par par = new Par (oglas1, oglas2, false);

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
							stvoriKand(oglasA, oglasC);
							stvoriKand(oglasB, oglasA);
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

		//iteriramo po oglasima a ne lajkovima kako bi polje ocjena bilo sortirano po vremenu stvaranja oglasa
		//tj tko prije stvori oglas taj ima vecu prednost u matchanju
		//zato je polje oglasi sortirano po datumu objave
		for (Oglas oglas : oglasi){
			UUID studentID = oglas.getStudent().getId();
			List<Lajk> lajkovi = lajkService.listAll();
			for (Lajk lajk : lajkovi){
				if (lajk.getLajkId().getStudent().getId() == studentID) {
					UUID id1 = oglas.getId_oglas();
					UUID id2 = lajk.getLajkId().getOglas().getId_oglas();
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

			//racuanje obostranih ocjena i pospremanje u rijecnik
			Hashtable<Par, Integer> obostraneOcjene = new Hashtable<Par, Integer>();
			List<Par> parovi = parService.listAll();
			for (Par par : parovi){
				Key key1 = new Key(par.getOglas1().getId_oglas(), par.getOglas2().getId_oglas());
				Key key2 = new Key(par.getOglas2().getId_oglas(), par.getOglas1().getId_oglas());
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
			}

			//stvaranje konacnog para i postavljanje statusa oba oglasa u CEKA
			for (Oglas oglas2 : oglasi){
				Optional<Par> konacniParOptional = parovi
						.stream()
						.filter(parSa -> parService.parSadrziOglas(parSa, oglas2))
						.filter(parAl -> parService.oglasiSuAKTIVAN(parAl)) //t ako su stanja oba oglasa == AKTIVAN
						.sorted(Comparator.comparing(parSr -> obostraneOcjene.get(parSr)))
						.findFirst();

				konacniParOptional.ifPresent(
						(konacniPar) ->
						{
							Par par = parovi.get(konacniPar.getIdPar());
							parService.save(par);
							parService.rezervirajOglasePara(par);
						}
				);
			}
		}



	}

	@Override
	public void confirmFun() {
		//Cita rezultat konacnih potvrda para i:
		//ako su oba studenta prihvatila: oznacuje par.done = true, oglas.status=POTVRDEN i salje mail u SC
		//ako oba studenta nisu prihvatila: oznacuje oba entiteta kandidat i par sa ignore (vise se ne spajaju/gledaju)

		//tj, ako je lanac=true onda gledamo 3 oglasa, ne samo 2

		List<Par> parovi = parService.listAll();
		for (Par par : parovi){

			//ako nemamo lanac
			if (par.getLanac() == false){
				//oba studenta su prihvatila par
				if (parService.ifObaAKTIVAN(par) && par.getIgnore() == false){
					par.setDone(true);
					MailService.PotvrdaZamjenePara(par);
				}

				//barem jedan od studenata nije prihvatio par
				//odbijanjem para treba odraditi par.setIgnore(true)
				if (!parService.ifObaAKTIVAN(par) && par.getIgnore() == true){
					Optional<Kandidat> kandidatOptional = par.getOglas1().getKandidati()
							.stream()
							.filter(kand -> kand.getIdOglas() == par.getOglas1().getId_oglas()
									&& kand.getKandOglas().getId_oglas() == par.getOglas2().getId_oglas())
							.findFirst();

					kandidatOptional.ifPresent(kandidat -> kandidat.setIgnore(true));
				}
			}
			//ako imamo lanac
			else {
				//todo
			}



		}
	}

	@Override
	public void confirmSCFun(List<Par> izvedeni) {
		//Prolazi po predanoj listi 'izvedeni' i oznacava oglase nevednih parova kao IZVEDEN

		for (Par par : izvedeni) {
			var oglas1 = par.getOglas1();
			oglas1.getStatus().setStatus(StatusOglasaEnum.IZVEDEN);

			var oglas2 = par.getOglas2();
			oglas2.getStatus().setStatus(StatusOglasaEnum.IZVEDEN);
		}
	}
}
