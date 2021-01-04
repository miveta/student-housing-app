package progi.projekt.launch;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import progi.projekt.model.*;
import progi.projekt.model.enums.BrojKrevetaEnum;
import progi.projekt.model.enums.OznakeKategorijaEnum;
import progi.projekt.model.enums.StatusOglasaEnum;
import progi.projekt.model.enums.TipKupaoniceEnum;
import progi.projekt.repository.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseFill implements ApplicationListener<ContextRefreshedEvent> {

    private final PasswordEncoder pswdEncoder;

    //TODO: kada su rjeseni database controlleri za umetanje, ovo bi bilo dobro izvest prek njih
    private final StudentRepository studentRepository;
    private final DomRepository domRepository;
    private final GradRepository gradRepository;
    private final ObavijestRepository obavijestRepository;
    private final OglasRepository oglasRepository;
    private final StatusOglasaRepository statusOglasaRepository;
    private final TrazeniUvjetiRepository trazeniUvjetiRepository;
    private final ZaposlenikscRepository zaposlenikscRepository;
    private final StudentskiCentarRepository studentskiCentarRepository;
    private final SobaRepository sobaRepository;
    private final PaviljonRepository paviljonRepository;
    private final BrojKrevetaRepository brojKrevetaRepository;
    private final TipKupaoniceRepository tipKupaoniceRepository;


    public DatabaseFill(StudentRepository studentRepository, DomRepository domRepository,
                        GradRepository gradRepository, ObavijestRepository obavijestRepository,
                        OglasRepository oglasRepository, StatusOglasaRepository statusOglasaRepository,
                        TrazeniUvjetiRepository trazeniUvjetiRepository, ZaposlenikscRepository zaposlenikscRepository,
                        StudentskiCentarRepository studentskiCentarRepository, SobaRepository sobaRepository,
                         PaviljonRepository paviljonRepository, PasswordEncoder pswdEncoder,
                        BrojKrevetaRepository brojKrevetaRepository, TipKupaoniceRepository tipKupaoniceRepository) {
        this.studentRepository = studentRepository;
        this.domRepository = domRepository;
        this.gradRepository = gradRepository;
        this.obavijestRepository = obavijestRepository;
        this.oglasRepository = oglasRepository;
        this.statusOglasaRepository = statusOglasaRepository;
        this.trazeniUvjetiRepository = trazeniUvjetiRepository;
        this.zaposlenikscRepository = zaposlenikscRepository;
        this.studentskiCentarRepository = studentskiCentarRepository;
        this.pswdEncoder = pswdEncoder;
        this.sobaRepository = sobaRepository;
        this.paviljonRepository = paviljonRepository;
        this.brojKrevetaRepository = brojKrevetaRepository;
        this.tipKupaoniceRepository = tipKupaoniceRepository;

    }


    private String hashPassword(String password) throws NoSuchAlgorithmException {
        /*
        MessageDigest digest = getInstance("SHA-256");

        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(hash);

        return encoded;
        */

        return pswdEncoder.encode(password);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //Izvedi skriptu jedino ako nista nije u bazi (inace baca error za umetanje identicnih vrijednosti)
        //LAJKOVI NE RADE???? nisam probo
        try {
            if (studentRepository.count() == 0) {
                //Kreiraj studente
                Student ivica = new Student();
                ivica.setEmail("ivica@gmail.com");
                ivica.setIme("Ivica");
                ivica.setPrezime("Ivic");
                ivica.setKorisnickoIme("ivi");
                ivica.setLozinka(hashPassword("123456"));
                ivica.setObavijestiNaMail(false);
                ivica.setJmbag("0036567891");

                Student marko = new Student();
                marko.setEmail("marko@gmail.com");
                marko.setIme("Marko");
                marko.setPrezime("Markic");
                marko.setKorisnickoIme("mark32");
                marko.setLozinka(hashPassword("password"));
                marko.setObavijestiNaMail(false);
                marko.setJmbag("0036563532");

                Student pero = new Student();
                pero.setEmail("pero@gmail.com");
                pero.setIme("Pero");
                pero.setPrezime("Peric");
                pero.setKorisnickoIme("peri21");
                pero.setLozinka(hashPassword("654321"));
                pero.setObavijestiNaMail(false);
                pero.setJmbag("0036565167");

                //Kreiraj SC
                StudentskiCentar studentskiCentar = new StudentskiCentar();
                studentskiCentar.setNaziv("SC Zagreb");

                //Kreiraj zaposlenika SC-a
                ZaposlenikSC stefko = new ZaposlenikSC();
                stefko.setEmail("stefko@gmail.com");
                stefko.setIme("Stefko");
                stefko.setPrezime("Stefic");
                stefko.setLozinka(hashPassword("stefko"));
                stefko.setKorisnickoIme("stef567");

                //Kreiraj grad
                Grad osijek = new Grad();
                osijek.setNaziv("Osijek");

                //Kreiraj domove
                Dom osijek1 = new Dom();
                Dom osijek2 = new Dom();
                osijek1.setImaMenzu(true);
                osijek1.setNaziv("Osijek1");
                osijek2.setImaMenzu(false);
                osijek2.setNaziv("Osijek2");

                //Kreiraj grad
                Grad zagreb = new Grad();
                zagreb.setNaziv("Zagreb");

                //Kreiraj domove
                Dom sava = new Dom();
                Dom radic = new Dom();
                sava.setImaMenzu(true);
                sava.setNaziv("Sava");
                radic.setImaMenzu(false);
                radic.setNaziv("Radic");

                //Kreiraj trazene uvjete
                TrazeniUvjeti uvjetiMarko = new TrazeniUvjeti();
                TrazeniUvjeti uvjetiPero = new TrazeniUvjeti();
                TrazeniUvjeti uvjetiIvica = new TrazeniUvjeti();
                uvjetiIvica.setKategorija(OznakeKategorijaEnum.II);
                uvjetiIvica.setGodina(2020);
                uvjetiIvica.setKomentar("Trazim sobu!");

                uvjetiMarko.setKategorija(OznakeKategorijaEnum.I);
                uvjetiMarko.setGodina(2020);
                uvjetiMarko.setKomentar("Trazim i ja sobu!");


                uvjetiPero.setKategorija(OznakeKategorijaEnum.II);
                uvjetiPero.setGodina(2020);
                uvjetiPero.setKomentar("");

                //Kreiraj oglase
                Oglas oglasMarko = new Oglas();
                Oglas oglasPero = new Oglas();
                Oglas oglasIvica = new Oglas();
                oglasIvica.setGodina(2020);
                oglasIvica.setNaslov("Trokrevetna soba kategorije II");
                oglasIvica.setOpis("Dobro ocuvana soba na drugom katu, blizu menze");
                oglasIvica.setObjavljen(Date.valueOf("2020-10-01"));

                oglasMarko.setGodina(2020);
                oglasMarko.setNaslov("Jednokrevetna soba kategorije I");
                oglasMarko.setOpis("Dobro ocuvana soba na trecem katu");
                oglasMarko.setObjavljen(Date.valueOf("2020-10-05"));

                oglasPero.setGodina(2020);
                oglasPero.setNaslov("Dvokrevetna soba kategorije II");
                oglasPero.setOpis("/");
                oglasPero.setObjavljen(Date.valueOf("2020-10-07"));

                //Kreiraj statuse
                StatusOglasa statusMarko = new StatusOglasa();
                StatusOglasa statusPero = new StatusOglasa();
                StatusOglasa statusIvica = new StatusOglasa();
                statusIvica.setStatus(StatusOglasaEnum.POTVRDEN);
                statusMarko.setStatus(StatusOglasaEnum.AKTIVAN);
                statusPero.setStatus(StatusOglasaEnum.AKTIVAN);

                //Kreiraj obavijest
                Obavijest obavijestZaIvicu = new Obavijest();
                obavijestZaIvicu.setProcitana(false);
                obavijestZaIvicu.setTekst("Pero je potvrdio tvoj oglas!");
                obavijestZaIvicu.setVrijeme(Date.valueOf("2020-10-8"));

                //Kreiraj soba
                Soba sobaIvica = new Soba();
                sobaIvica.setBroj(1);

                sobaIvica.setKategorija(OznakeKategorijaEnum.IV);

                Soba sobaMarko = new Soba();
                sobaMarko.setBroj(1);

                sobaMarko.setKategorija(OznakeKategorijaEnum.II);

                Soba sobaPero = new Soba();
                sobaPero.setBroj(5);

                sobaPero.setKategorija(OznakeKategorijaEnum.III);



                //Kreiraj paviljon
                Paviljon paviljonSava = new Paviljon();
                Paviljon paviljonRadic = new Paviljon();
                paviljonSava.setNaziv("Prvi");
                paviljonRadic.setNaziv("Peti");

                //Linkaj sve------------------------------------------------------------------------------


                //ASSIGN OGLAS TO SOBA
//                sobaMarko.setOglas(oglasMarko);
//                sobaPero.setOglas(oglasPero);
//                sobaIvica.setOglas(oglasIvica);

                //ASSIGN DOM TO PAVILJON
                paviljonSava.setDom(sava);
                paviljonRadic.setDom(radic);




                //ASSIGN OGLAS TO STUDENT
                ivica.setOglas(oglasIvica);
                marko.setOglas(oglasMarko);
                pero.setOglas(oglasPero);

                //ASSIGN TRAZENIUVJETI TO  STUDENT
                ivica.setUvjeti(uvjetiIvica);
                marko.setUvjeti(uvjetiMarko);
                pero.setUvjeti(uvjetiPero);

                //ASSIGN POTVRDENIOGLAS TO STUDENT
                pero.setPotvrdioOglas(statusIvica);

                //ASSIGN GRAD TO STUDENT
                ivica.setGrad(zagreb);
                pero.setGrad(zagreb);
                marko.setGrad(zagreb);

                //ASSIGN ZAPOSLENIKE TO SC
                ArrayList<ZaposlenikSC> zaposlenici = new ArrayList<>();
                zaposlenici.add(stefko);
                studentskiCentar.setZaposlenici(zaposlenici);

                //ASSIGN GRAD TO SC
                studentskiCentar.setGrad(zagreb);

                //ASSIGN SC TO ZAPOSLENIK
                stefko.setZaposlenSC(studentskiCentar);

                //ASSIGN DOMOVI TO GRAD
                HashSet<Dom> domoviOsije = new HashSet<>();
                domoviOsije.add(osijek1);
                domoviOsije.add(osijek2);
                osijek.setDomovi(domoviOsije);

                osijek1.setGrad(osijek);
                osijek2.setGrad(osijek);

                //ASSIGN DOMOVI TO GRAD
                HashSet<Dom> domovi = new HashSet<>();
                domovi.add(radic);
                domovi.add(sava);
                zagreb.setDomovi(domovi);



                //ASSIGN SC TO GRAD
                zagreb.setStudentskiCentar(studentskiCentar);

                //ASSIGN GRAD TO DOMO
                radic.setGrad(zagreb);
                sava.setGrad(zagreb);

                //ASSIGN PAVILJONI TO DOM
                HashSet<Paviljon> paviljoniRadic = new HashSet<>();
                HashSet<Paviljon> paviljoniSava = new HashSet<>();
                paviljoniRadic.add(paviljonRadic);
                paviljoniSava.add(paviljonSava);
                radic.setPaviljoni(paviljoniRadic);
                sava.setPaviljoni(paviljoniSava);

                //ASSIGN STUDENT TO TRAZIUVJETI
                uvjetiIvica.setTraziStudent(ivica);
                uvjetiMarko.setTraziStudent(marko);
                uvjetiPero.setTraziStudent(pero);

                //ASSIGN STATUS TO OGLAS
                oglasIvica.setStatus(statusIvica);
                oglasMarko.setStatus(statusMarko);
                oglasPero.setStatus(statusPero);

                //ASSIGN STUDENT TO OGLAS
                oglasIvica.setStudent(ivica);
                oglasMarko.setStudent(marko);
                oglasPero.setStudent(pero);

                //ASSIGN SOBA TO OGLAS
            //    oglasIvica.setSoba(sobaIvica);
            //    oglasMarko.setSoba(sobaMarko);
            //    oglasPero.setSoba(sobaPero);

                //ASSIGN OGLAS TO STATUS
                statusIvica.setOglas(oglasIvica);
                statusMarko.setOglas(oglasMarko);
                statusPero.setOglas(oglasPero);

                //ASSIGN POTVRDIOSTUDENT TO STATUS
                statusIvica.setPotvrdioOglas(pero);


                //ASSIGN OGLAS TO OBAVIJEST
                obavijestZaIvicu.setOglas(oglasIvica);

                //ASSIGN IVICA TO OBAVIJEST
                ArrayList<Student> ivicaList = new ArrayList<>();
                ivicaList.add(ivica);
                //obavijestZaIvicu.setStudent(ivicaList);


                //Saveaj sve---------------------------------------------------------------------------
                HashSet<Student> studentiZaSave = new HashSet<>();
                studentiZaSave.add(ivica);
                studentiZaSave.add(marko);
                studentiZaSave.add(pero);

                HashSet<TrazeniUvjeti> trazeniUvjeti = new HashSet<>();
                trazeniUvjeti.add(uvjetiIvica);
                trazeniUvjeti.add(uvjetiMarko);
                trazeniUvjeti.add(uvjetiPero);

                HashSet<StatusOglasa> statusiOglasa = new HashSet<>();
                statusiOglasa.add(statusIvica);
                statusiOglasa.add(statusMarko);
                statusiOglasa.add(statusPero);

                HashSet<Oglas> oglasi = new HashSet<>();
                oglasi.add(oglasIvica);
                oglasi.add(oglasMarko);
                oglasi.add(oglasPero);



                HashSet<Paviljon> paviljoni = new HashSet<>();
                paviljoni.add(paviljonRadic);
                paviljoni.add(paviljonSava);

                HashSet<Soba> sobe = new HashSet<>();
                sobe.add(sobaIvica);
                sobe.add(sobaMarko);
                sobe.add(sobaPero);

                gradRepository.save(zagreb);
                gradRepository.save(osijek);
//                oglasRepository.saveAll(oglasi);
                zaposlenikscRepository.save(stefko);
                studentskiCentarRepository.save(studentskiCentar);
                domRepository.saveAll(domovi);   //domovi napravljena u procesu linkanja iznad
                obavijestRepository.save(obavijestZaIvicu);
                trazeniUvjetiRepository.saveAll(trazeniUvjeti);
                statusOglasaRepository.saveAll(statusiOglasa);
                studentRepository.saveAll(studentiZaSave);
                paviljonRepository.saveAll(paviljoni);
                sobaRepository.saveAll(sobe);

                System.out.println("Umetnute pocetne vrijednosti");

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
