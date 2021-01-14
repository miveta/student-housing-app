package progi.projekt.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import progi.projekt.model.Kandidat;
import progi.projekt.model.Oglas;
import progi.projekt.model.Par;
import progi.projekt.model.enums.StatusOglasaEnum;
import progi.projekt.repository.OglasRepository;
import progi.projekt.repository.ParRepository;
import progi.projekt.service.KandidatService;
import progi.projekt.service.ObavijestService;
import progi.projekt.service.ParService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParServiceImpl implements ParService {

	private ParRepository parRepo;
	private KandidatService kandidatService;
	private OglasRepository oglasRepo;
	private ObavijestService obavijestService;

	public ParServiceImpl(ParRepository parRepo, @Lazy KandidatService kandidatService, OglasRepository oglasRepo) {
		this.parRepo = parRepo;
		this.kandidatService = kandidatService;
		this.oglasRepo = oglasRepo;
		this.obavijestService = obavijestService;
	}

	@Override
	public List<Par> listAll() {
		return parRepo.findAll();
	}

	@Override
	public List<Par> listAll(Oglas oglas) {
		return parRepo.findAllByOglas1OrOglas2(oglas,oglas);
	}

	@Override
	public Optional<Par> find(Long idPar) {
		return parRepo.findByIdPar(idPar);
	}

	@Override
	public void ponistiPar(Par par) {
		par.setIgnore(true);
		save(par);
	}

	@Override
	public Integer ObostranaOcjena(Integer ocjena1, Integer ocjena2) {
		return ocjena1 + ocjena2;
	}

	@Override
	public boolean parSadrziOglas(Par par, Oglas oglas) {
		return par.getOglas1() == oglas || par.getOglas2() == oglas;
	}

	@Override
	public void rezervirajOglasePara(Par par) {
		Oglas oglas1 = par.getOglas1();
		Oglas oglas2 = par.getOglas2();

		if (par.getLanac() == false){
			oglas1.setStatusOglasa(StatusOglasaEnum.CEKA);
			oglas2.setStatusOglasa(StatusOglasaEnum.CEKA);

			oglas1.setKonacniPar(par);
			oglas2.setKonacniPar(par);

			oglasRepo.save(oglas1);
			oglasRepo.save(oglas2);

			//todo:
			//oglas1.obavijestiService.dodajPotvrdiZamjenuObavijest(par);
		} else {
			oglas1.setStatusOglasa(StatusOglasaEnum.CEKA);
			oglas1.setKonacniPar(par);

			oglasRepo.save(oglas1);

			//todo:
			//oglas1.obavijestiService.dodajPotvrdiZamjenuObavijest(oglas1);
		}
	}

	@Override
	public void potvrdiOglasePara(Par par) {
		Oglas oglas1 = par.getOglas1();
		Oglas oglas2 = par.getOglas2();

		oglas1.setStatusOglasa(StatusOglasaEnum.POTVRDEN);
		oglas2.setStatusOglasa(StatusOglasaEnum.POTVRDEN);

		oglasRepo.save(oglas1);
		oglasRepo.save(oglas2);

		//par.setCeka(true);

		obavijestService.notifyExchanged(oglas1, oglas2);
		obavijestService.notifyExchanged(oglas2, oglas1);
	}

	@Override
	public void vratiOglaseParaNaAKTIVAN(Par par) {
		Oglas oglas1 = par.getOglas1();
		Oglas oglas2 = par.getOglas2();

		oglas1.setStatusOglasa(StatusOglasaEnum.AKTIVAN);
		oglas2.setStatusOglasa(StatusOglasaEnum.AKTIVAN);

		oglasRepo.save(oglas1);
		oglasRepo.save(oglas2);
	}

	@Override
	public boolean save(Par par) {
		List<Par> parovi = listAll();
		for (Par parTmp : parovi){
			if (par.getLanac() == false){
				//ako nije lanac, nema duplikata
				if (parTmp.getOglas1() == par.getOglas1() && parTmp.getOglas2() == par.getOglas2()) return false;
				if (parTmp.getOglas1() == par.getOglas2() && parTmp.getOglas2() == par.getOglas1()) return false;
			} else {
				//ako je lanac, 1 duplikat je u redu
				List<Par> lanac = pripadniParoviLanca(par.getOglas1());
				List<Par> lanacTmp = pripadniParoviLanca(parTmp.getOglas1());
				boolean exitFlag = false;
				if (lanac.size() == 3 && lanacTmp.size() == 3){
					for (int i = 0; i<3; i++){
						if (lanac.get(i) == lanacTmp.get(i) && exitFlag == false) exitFlag = true;
						else if (lanac.get(i) == lanacTmp.get(i) && exitFlag == true) return false;
					}
				}

			}
		}
		if (par.getOglas1() == par.getOglas2()) return false;
		parRepo.save(par);
		return true;
	}

	@Override
	public void update(Par par) {
		parRepo.save(par);
	}

	@Override
	public boolean ifObaAKTIVAN(Par par) {
		var statusPrvogOglasa = par.getOglas1().getStatusOglasa();
		var statusDrugogOgalsa = par.getOglas2().getStatusOglasa();

		return statusPrvogOglasa == StatusOglasaEnum.AKTIVAN && statusDrugogOgalsa == StatusOglasaEnum.AKTIVAN;
	}

	@Override
	public boolean ifObaCEKA(Par par) {
		var statusPrvogOglasa = par.getOglas1().getStatusOglasa();
		var statusDrugogOgalsa = par.getOglas2().getStatusOglasa();

		return statusPrvogOglasa == StatusOglasaEnum.CEKA && statusDrugogOgalsa == StatusOglasaEnum.CEKA;
	}

	@Override
	public Integer TrostranaOcjena(Integer ocjenaAB, Integer ocjenaBC, Integer ocjenaCA) {
		return (ocjenaAB + ocjenaBC + ocjenaCA) * 2/3;
	}

	//primi par i vrati oglas3, gdje je oglas 3 treci oglas lanaca
	@Override
	public List<Oglas> pronadjiTreciOglasIzLanca(Par par) {
		List<Par> paroviC = listAll();
		List<Oglas> oglasiC = new ArrayList<>();
		Optional<Oglas> oglas3Opt = Optional.empty();
		for (Par parC : paroviC){
			if 		(parSadrziOglas(parC, par.getOglas2()) &&
					!parSadrziOglas(parC, par.getOglas1()) &&
					par.getLanac() == true
			){
				if (parC.getOglas1() == par.getOglas2()){
					oglas3Opt = Optional.ofNullable(parC.getOglas2());
				} else {
					oglas3Opt = Optional.ofNullable(parC.getOglas1());
				}
				oglas3Opt.ifPresent(oglas -> oglasiC.add(oglas));
			}
		}
		return oglasiC;
	}

	//primi oglas1 i vrati listu [oglas1, oglas2, oglas3, 4, 5, 6, ...], gdje oglasi 1-3 cine prvi lanac, 4-6 drugi...
	@Override
	public List<Oglas> pripadniOglasiLanca(Oglas oglasA) {
		List<Oglas> lanacOglasi = new ArrayList<>();
		try{
			List<Par> paroviAB = pripadniParoviOglasa(oglasA);
			List<Oglas> oglasi2 = new ArrayList<>();;
			for (Par parAB : paroviAB){
				if (parAB.getLanac() == true && parAB.getOglas1() == oglasA){
					oglasi2.add(parAB.getOglas2());
				} else {
					oglasi2.add(parAB.getOglas1());
				}
				for (Oglas oglasB : oglasi2){
					List<Par> paroviBC = pripadniParoviOglasa(oglasB);

					for (Par parBC : paroviBC){
						if (parBC.getLanac() == true && parBC.getOglas1() == oglasB){
							List<Oglas> oglasiC = pronadjiTreciOglasIzLanca(parBC);

							for (Oglas oglasC : oglasiC){
								lanacOglasi.add(oglasA);
								lanacOglasi.add(oglasB);
								lanacOglasi.add(oglasC);
							}
						}
					}

				}

			}


		} catch (Exception e){
			return lanacOglasi;
		}

		return lanacOglasi;
	}

	//primi oglas1 i vrati listu [parAB, parBC, parCA] koji zajedno cine lanac
	@Override
	public List<Par> pripadniParoviLanca(Oglas oglas1) {
		List<Oglas> lanacOglasi = pripadniOglasiLanca(oglas1);

		List<Par> lanacParovi = new ArrayList<>();

		Par parAB = null;
		Par parBC = null;
		Par parCA = null;

		try {
			parAB = pripadniParAB(lanacOglasi.get(0), lanacOglasi.get(1)).get();
			parBC = pripadniParAB(lanacOglasi.get(1), lanacOglasi.get(2)).get();
			parCA = pripadniParAB(lanacOglasi.get(2), lanacOglasi.get(0)).get();

			lanacParovi.add(parAB);
			lanacParovi.add(parBC);
			lanacParovi.add(parCA);

		} catch (Exception e){
			//ciscenje malformed lanca
//			if (parAB != null) {
//				ponistiPar(parAB);
//			}
//			if (parBC != null) {
//				ponistiPar(parBC);
//			}
//			if (parCA != null) {
//				ponistiPar(parCA);
//			}

			//zakomentirano jer radi inf loop (save -> pripadniParoviLanca -> ponistiPar -> save ...)

			return lanacParovi;
		}

		return lanacParovi;
	}

	@Override
	public Optional<Par> pripadniParDvaOglasa(Oglas oglas1, Oglas oglas2) {
		Optional<Par> parOpt = Optional.empty();

		List<Par> parovi = listAll();
		for (Par par : parovi){
			if (	par.getOglas1() == oglas1 && par.getOglas2() == oglas2 ||
					par.getOglas1() == oglas2 && par.getOglas2() == oglas1){
				parOpt = Optional.of(par);
				break;
			}
		}
		return parOpt;
	}

	@Override
	public List<Par> pripadniParoviOglasa (Oglas oglas) {
		List<Par> parovi = new ArrayList<Par>();

		List<Par> paroviC = listAll();
		for (Par parC : paroviC){
			if (!parC.getLanac()) {
				if (parSadrziOglas(parC, oglas)){
					parovi.add(parC);
				}
			}
			else {
				if (parC.getOglas1() == oglas){
					parovi.add(parC);
				}
			}

		}
		return parovi;
	}

	//note: ova metoda je predvidjena za rad sa lancima pa je
	// redoslijed poslanih argumenata bitan
	@Override
	public Optional<Par> pripadniParAB(Oglas oglasA, Oglas oglasB) {
		Optional<Par> par = Optional.empty();

		List<Par> paroviC = listAll();
		for (Par parC : paroviC){
			if (parC.getOglas1() == oglasA && parC.getOglas2() == oglasB){
				par = Optional.of(parC);
				break;
			}
		}
		return par;
	}

	@Override
	public Optional<Kandidat> pripadniKandidatPara(Par par) {
		Optional<Kandidat> kandidat = kandidatService.kandidatParaOglasa(par.getOglas1(), par.getOglas2());
		return kandidat;
	}


	@Override
	public void ponistiParoveOglasa(Oglas oglas) {
		List<Par> parovi = listAll();
		for (Par par : parovi){
			if (parSadrziOglas(par, oglas) && !par.getDone()) {
				par.setIgnore(true);
				save(par);
			}

		}
	}

	@Override
	public boolean obaStudPrihvatila(Par par) {
		return par.getPrihvatioPrvi() == true && par.getPrihvatioDrugi() == true;
	}

	@Override
	public boolean josNisuPar(Oglas oglas, Oglas kand) {
		List<Par> parovi = pripadniParoviOglasa(oglas);
		for (Par par: parovi){
			if (parSadrziOglas(par, kand) && par.getLanac() == false) return false;
		}

		return true;
	}

	@Override
	public boolean josNisuLanac(Oglas oglas, Oglas kand) {
		List<Oglas> lanac = pripadniOglasiLanca(oglas);
		if (lanac.size() != 0){
			return false;
		}
		return true;
	}
}
