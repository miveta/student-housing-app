package progi.projekt.launch;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.mail.javamail.JavaMailSender;
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
import java.util.Collections;
import java.util.HashSet;

@Component
public class DatabaseFill implements ApplicationListener<ContextRefreshedEvent> {

    private final PasswordEncoder pswdEncoder;

    //TODO: kada su rjeseni database controlleri za umetanje, ovo bi bilo dobro izvest prek njih
    private final StudentRepository studentRepository;
    private final DomRepository domRepository;
    private final GradRepository gradRepository;
    private final ObavijestRepository obavijestRepository;
    private final OglasRepository oglasRepository;
    private final TrazeniUvjetiRepository trazeniUvjetiRepository;
    private final ZaposlenikscRepository zaposlenikscRepository;
    private final StudentskiCentarRepository studentskiCentarRepository;
    private final SobaRepository sobaRepository;

    private final PaviljonRepository paviljonRepository;

    private final JavaMailSender javaMailSender;

    public DatabaseFill(PasswordEncoder pswdEncoder, StudentRepository studentRepository, DomRepository domRepository, GradRepository gradRepository, ObavijestRepository obavijestRepository, OglasRepository oglasRepository, TrazeniUvjetiRepository trazeniUvjetiRepository, ZaposlenikscRepository zaposlenikscRepository, StudentskiCentarRepository studentskiCentarRepository, SobaRepository sobaRepository, PaviljonRepository paviljonRepository, JavaMailSender javaMailSender) {
        this.pswdEncoder = pswdEncoder;
        this.studentRepository = studentRepository;
        this.domRepository = domRepository;
        this.gradRepository = gradRepository;
        this.obavijestRepository = obavijestRepository;
        this.oglasRepository = oglasRepository;
        this.trazeniUvjetiRepository = trazeniUvjetiRepository;
        this.zaposlenikscRepository = zaposlenikscRepository;
        this.studentskiCentarRepository = studentskiCentarRepository;
        this.sobaRepository = sobaRepository;
        this.paviljonRepository = paviljonRepository;
        this.javaMailSender = javaMailSender;
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

        //Odkomentiraj ovo da posaljes mail denisu da si pokrenuo program
        /*
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject("Uvjeti sobe više ne pašu vašim zahtjevima");
        msg.setText("Mail radi!");
        msg.setTo("denis.durasinovic@gmail.com");
        javaMailSender.send(msg);
        System.out.println("Mail poslan");*/


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
                StudentskiCentar scOsijek = new StudentskiCentar();
                scOsijek.setNaziv("SC Osijek");

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
                Dom igk = new Dom();
                Dom kps = new Dom();
                igk.setImaMenzu(true);
                igk.setNaziv("Ivana Gorana Kovacica");
                kps.setImaMenzu(false);
                kps.setNaziv("Kralja Petra Svacica");

                //Kreiraj grad
                Grad zagreb = new Grad();
                zagreb.setNaziv("Zagreb");

                //Kreiraj domove
                Dom lascina = new Dom();
                Dom radic = new Dom();
                Dom starcevic = new Dom();
                Dom cvjetno = new Dom();
                lascina.setImaMenzu(true);
                lascina.setNaziv("Lascina");
                radic.setImaMenzu(true);
                radic.setNaziv("Radic");
                starcevic.setImaMenzu(false);
                starcevic.setNaziv("Starcevic");
                cvjetno.setImaMenzu(true);
                cvjetno.setNaziv("Cvjetno");


                //Kreiraj trazene uvjete
                TrazeniUvjeti uvjetiMarko = new TrazeniUvjeti();
                TrazeniUvjeti uvjetiPero = new TrazeniUvjeti();
                TrazeniUvjeti uvjetiIvica = new TrazeniUvjeti();

                uvjetiIvica.setKategorija(Collections.singleton(OznakeKategorijaEnum.II));
                uvjetiMarko.setKategorija(Collections.singleton(OznakeKategorijaEnum.I));
                uvjetiPero.setKategorija(Collections.singleton(OznakeKategorijaEnum.II));

                //Kreiraj oglase
                Oglas oglasMarko = new Oglas();
                Oglas oglasPero = new Oglas();
                Oglas oglasIvica = new Oglas();

                oglasIvica.setNaslov("Trokrevetna soba kategorije II");
                oglasIvica.setObjavljen(Date.valueOf("2020-10-01"));

                oglasMarko.setNaslov("Jednokrevetna soba kategorije I");
                oglasMarko.setObjavljen(Date.valueOf("2020-10-05"));

                oglasPero.setNaslov("Dvokrevetna soba kategorije II");
                oglasPero.setObjavljen(Date.valueOf("2020-10-07"));

                HashSet<TrazeniUvjeti> trazeniUvjeti = new HashSet<>();
                trazeniUvjeti.add(uvjetiIvica);
                trazeniUvjeti.add(uvjetiMarko);
                trazeniUvjeti.add(uvjetiPero);
                trazeniUvjetiRepository.saveAll(trazeniUvjeti);

                oglasIvica.setTrazeniUvjeti(uvjetiIvica);
                uvjetiIvica.setOglas(oglasIvica);

                oglasMarko.setTrazeniUvjeti(uvjetiMarko);
                uvjetiMarko.setOglas(oglasMarko);

                oglasPero.setTrazeniUvjeti(uvjetiPero);
                uvjetiPero.setOglas(oglasPero);


                //Kreiraj obavijest
                Obavijest obavijestZaIvicu = new Obavijest();
                obavijestZaIvicu.setProcitana(false);
                obavijestZaIvicu.setTekst("Pero je potvrdio tvoj oglas!");
                obavijestZaIvicu.setVrijeme(Date.valueOf("2020-10-8"));

                //Kreiraj soba
                Soba sobaIvica = new Soba();

                sobaIvica.setBrojKreveta(BrojKrevetaEnum.JEDNOKREVETNA);
                sobaIvica.setKomentar("mijenjam jer mi je daleko menza");
                sobaIvica.setTipKupaonice(TipKupaoniceEnum.DIJELJENA);
                Soba sobaMarko = new Soba();

                sobaMarko.setBrojKreveta(BrojKrevetaEnum.DVOKREVETNA);
                sobaMarko.setKomentar("komentarcic");
                sobaMarko.setTipKupaonice(TipKupaoniceEnum.PRIVATNA);

                Soba sobaPero = new Soba();
                sobaPero.setBrojKreveta(BrojKrevetaEnum.JEDNOKREVETNA);
                sobaPero.setKomentar("dobra soba");
                sobaPero.setTipKupaonice(TipKupaoniceEnum.DIJELJENA);


                //Kreiraj paviljone lascina
                Paviljon paviljonlascina1 = new Paviljon();
                paviljonlascina1.setNaziv("Prvi");
                paviljonlascina1.setBrojKatova(4);
                paviljonlascina1.setKategorija(OznakeKategorijaEnum.III);

                Paviljon paviljonlascina2 = new Paviljon();
                paviljonlascina2.setNaziv("Drugi");
                paviljonlascina2.setBrojKatova(5);
                paviljonlascina2.setKategorija(OznakeKategorijaEnum.II);

                Paviljon paviljonlascina3 = new Paviljon();
                paviljonlascina3.setNaziv("Treci");
                paviljonlascina3.setBrojKatova(4);
                paviljonlascina3.setKategorija(OznakeKategorijaEnum.I);

                sobaMarko.setPaviljon(paviljonlascina1);
                sobaIvica.setPaviljon(paviljonlascina1);
                sobaPero.setPaviljon(paviljonlascina1);
                HashSet<Soba> paviljonlascina1Sobe = new HashSet<>();
                paviljonlascina1Sobe.add(sobaMarko);
                paviljonlascina1Sobe.add(sobaIvica);
                paviljonlascina1Sobe.add(sobaPero);
                paviljonlascina1.setSobe(paviljonlascina1Sobe);

                //kreiraj paviljone radic
                Paviljon paviljonRadic1 = new Paviljon();
                paviljonRadic1.setNaziv("Peti");
                paviljonRadic1.setBrojKatova(4);
                paviljonRadic1.setKategorija(OznakeKategorijaEnum.I);

                Paviljon paviljonRadic2 = new Paviljon();
                paviljonRadic2.setNaziv("Sedmi");
                paviljonRadic2.setBrojKatova(5);
                paviljonRadic2.setKategorija(OznakeKategorijaEnum.III);

                Paviljon paviljonRadic3 = new Paviljon();
                paviljonRadic3.setNaziv("Deseti");
                paviljonRadic3.setBrojKatova(3);
                paviljonRadic3.setKategorija(OznakeKategorijaEnum.IV);

                //Kreiraj paviljone starcevic
                Paviljon paviljonStarcevic1 = new Paviljon();
                paviljonStarcevic1.setNaziv("Prvi");
                paviljonStarcevic1.setBrojKatova(5);
                paviljonStarcevic1.setKategorija(OznakeKategorijaEnum.III);

                Paviljon paviljonStarcevic2 = new Paviljon();
                paviljonStarcevic2.setNaziv("Drugi");
                paviljonStarcevic2.setBrojKatova(4);
                paviljonStarcevic2.setKategorija(OznakeKategorijaEnum.III);

                Paviljon paviljonStarcevic3 = new Paviljon();
                paviljonStarcevic3.setNaziv("Treci");
                paviljonStarcevic3.setBrojKatova(2);
                paviljonStarcevic3.setKategorija(OznakeKategorijaEnum.II);

                //Kreiraj paviljone cvjetno
                Paviljon paviljonCvjetno1 = new Paviljon();
                paviljonCvjetno1.setNaziv("Drugi");
                paviljonCvjetno1.setBrojKatova(4);
                paviljonCvjetno1.setKategorija(OznakeKategorijaEnum.I);

                Paviljon paviljonCvjetno2 = new Paviljon();
                paviljonCvjetno2.setNaziv("Cetvrti");
                paviljonCvjetno2.setBrojKatova(4);
                paviljonCvjetno2.setKategorija(OznakeKategorijaEnum.II);

                Paviljon paviljonCvjetno3 = new Paviljon();
                paviljonCvjetno3.setNaziv("Sedmi");
                paviljonCvjetno3.setBrojKatova(4);
                paviljonCvjetno3.setKategorija(OznakeKategorijaEnum.I);

                //Kreiraj paviljone IGK
                Paviljon paviljonIGK1 = new Paviljon();
                paviljonIGK1.setNaziv("Drugi");
                paviljonIGK1.setBrojKatova(4);
                paviljonIGK1.setKategorija(OznakeKategorijaEnum.IV);

                Paviljon paviljonIGK2 = new Paviljon();
                paviljonIGK2.setNaziv("Peti");
                paviljonIGK2.setBrojKatova(2);
                paviljonIGK2.setKategorija(OznakeKategorijaEnum.II);

                Paviljon paviljonIGK3 = new Paviljon();
                paviljonIGK3.setNaziv("Sesti");
                paviljonIGK3.setBrojKatova(2);
                paviljonIGK3.setKategorija(OznakeKategorijaEnum.II);

                //Kreiraj paviljone KPS
                Paviljon paviljonKPS1 = new Paviljon();
                paviljonKPS1.setNaziv("Treci");
                paviljonKPS1.setBrojKatova(2);
                paviljonKPS1.setKategorija(OznakeKategorijaEnum.II);

                Paviljon paviljonKPS2 = new Paviljon();
                paviljonKPS2.setNaziv("Sesti");
                paviljonKPS2.setBrojKatova(3);
                paviljonKPS2.setKategorija(OznakeKategorijaEnum.II);

                Paviljon paviljonKPS3 = new Paviljon();
                paviljonKPS3.setNaziv("Sedmi");
                paviljonKPS3.setBrojKatova(2);
                paviljonKPS3.setKategorija(OznakeKategorijaEnum.I);

                //Linkaj sve------------------------------------------------------------------------------


                //ASSIGN OGLAS TO SOBA
                sobaMarko.setOglas(oglasMarko);
                sobaPero.setOglas(oglasPero);
                sobaIvica.setOglas(oglasIvica);

                //ASSIGN DOM TO PAVILJON
                paviljonlascina1.setDom(lascina);
                paviljonlascina2.setDom(lascina);
                paviljonlascina3.setDom(lascina);
                paviljonRadic1.setDom(radic);
                paviljonRadic2.setDom(radic);
                paviljonRadic3.setDom(radic);
                paviljonStarcevic1.setDom(starcevic);
                paviljonStarcevic2.setDom(starcevic);
                paviljonStarcevic3.setDom(starcevic);
                paviljonCvjetno1.setDom(cvjetno);
                paviljonCvjetno2.setDom(cvjetno);
                paviljonCvjetno3.setDom(cvjetno);
                paviljonIGK1.setDom(igk);
                paviljonIGK2.setDom(igk);
                paviljonIGK3.setDom(igk);
                paviljonKPS1.setDom(kps);
                paviljonKPS2.setDom(kps);
                paviljonKPS3.setDom(kps);


                HashSet<Oglas> oglasiIvica = new HashSet<>();
                oglasiIvica.add(oglasIvica);
                //ASSIGN OGLAS TO STUDENT
                ivica.setOglasi(oglasiIvica);

                HashSet<Oglas> oglasiMarko = new HashSet<>();
                oglasiMarko.add(oglasMarko);
                marko.setOglasi(oglasiMarko);

                HashSet<Oglas> oglasiPero = new HashSet<>();
                oglasiPero.add(oglasPero);
                pero.setOglasi(oglasiPero);


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
                scOsijek.setGrad(osijek);

                //ASSIGN SC TO ZAPOSLENIK
                stefko.setZaposlenSC(studentskiCentar);

                //ASSIGN DOMOVI TO GRAD
                HashSet<Dom> domoviOsijek = new HashSet<>();
                domoviOsijek.add(igk);
                domoviOsijek.add(kps);
                osijek.setDomovi(domoviOsijek);

                //ASSIGN DOMOVI TO GRAD
                HashSet<Dom> domoviZagreb = new HashSet<>();
                domoviZagreb.add(radic);
                domoviZagreb.add(lascina);
                domoviZagreb.add(starcevic);
                domoviZagreb.add(cvjetno);
                zagreb.setDomovi(domoviZagreb);


                //ASSIGN SC TO GRAD
                zagreb.setStudentskiCentar(studentskiCentar);
                osijek.setStudentskiCentar(scOsijek);

                //ASSIGN GRAD TO DOMO
                radic.setGrad(zagreb);
                lascina.setGrad(zagreb);
                starcevic.setGrad(zagreb);
                cvjetno.setGrad(zagreb);

                igk.setGrad(osijek);
                kps.setGrad(osijek);

                //ASSIGN PAVILJONI TO DOM
                HashSet<Paviljon> paviljoniRadic = new HashSet<>();
                HashSet<Paviljon> paviljonilascina = new HashSet<>();
                HashSet<Paviljon> paviljonicvjetno = new HashSet<>();
                HashSet<Paviljon> paviljonistarcevic = new HashSet<>();
                HashSet<Paviljon> paviljoniIGK = new HashSet<>();
                HashSet<Paviljon> paviljoniKPS = new HashSet<>();

                paviljoniRadic.add(paviljonRadic1);
                paviljoniRadic.add(paviljonRadic2);
                paviljoniRadic.add(paviljonRadic3);

                paviljonilascina.add(paviljonlascina1);
                paviljonilascina.add(paviljonlascina2);
                paviljonilascina.add(paviljonlascina3);

                paviljonicvjetno.add(paviljonCvjetno1);
                paviljonicvjetno.add(paviljonCvjetno2);
                paviljonicvjetno.add(paviljonCvjetno3);

                paviljonistarcevic.add(paviljonStarcevic1);
                paviljonistarcevic.add(paviljonStarcevic2);
                paviljonistarcevic.add(paviljonStarcevic3);

                paviljoniIGK.add(paviljonIGK1);
                paviljoniIGK.add(paviljonIGK2);
                paviljoniIGK.add(paviljonIGK3);

                paviljoniKPS.add(paviljonKPS1);
                paviljoniKPS.add(paviljonKPS2);
                paviljoniKPS.add(paviljonKPS3);

                radic.setPaviljoni(paviljoniRadic);
                lascina.setPaviljoni(paviljonilascina);
                cvjetno.setPaviljoni(paviljonicvjetno);
                starcevic.setPaviljoni(paviljonistarcevic);
                igk.setPaviljoni(paviljoniIGK);
                kps.setPaviljoni(paviljoniKPS);

                //ASSIGN STATUS TO OGLAS
                oglasIvica.setStatusOglasa(StatusOglasaEnum.AKTIVAN);
                oglasMarko.setStatusOglasa(StatusOglasaEnum.POTVRDEN);
                oglasPero.setStatusOglasa(StatusOglasaEnum.IZVEDEN);

                //ASSIGN STUDENT TO OGLAS
                oglasIvica.setStudent(ivica);
                oglasMarko.setStudent(marko);
                oglasPero.setStudent(pero);

                //ASSIGN SOBA TO OGLAS
                oglasIvica.setSoba(sobaIvica);
                oglasMarko.setSoba(sobaMarko);
                oglasPero.setSoba(sobaPero);

                //ASSIGN OGLAS TO OBAVIJEST
                obavijestZaIvicu.setOglas(oglasIvica);

                //ASSIGN IVICA TO OBAVIJEST
                ArrayList<Student> ivicaList = new ArrayList<>();
                ivicaList.add(ivica);
                ivicaList.add(marko);
                obavijestZaIvicu.setStudent(ivicaList);


                //Saveaj sve---------------------------------------------------------------------------
                HashSet<Student> studentiZaSave = new HashSet<>();
                studentiZaSave.add(ivica);
                studentiZaSave.add(marko);
                studentiZaSave.add(pero);


                HashSet<Oglas> oglasi = new HashSet<>();
                oglasi.add(oglasIvica);
                oglasi.add(oglasMarko);
                oglasi.add(oglasPero);

                HashSet<Paviljon> paviljoni = new HashSet<>();
                paviljoni.add(paviljonRadic1);
                paviljoni.add(paviljonRadic2);
                paviljoni.add(paviljonRadic3);
                paviljoni.add(paviljonlascina1);
                paviljoni.add(paviljonlascina2);
                paviljoni.add(paviljonlascina3);
                paviljoni.add(paviljonCvjetno1);
                paviljoni.add(paviljonCvjetno2);
                paviljoni.add(paviljonCvjetno3);
                paviljoni.add(paviljonStarcevic1);
                paviljoni.add(paviljonStarcevic2);
                paviljoni.add(paviljonStarcevic3);
                paviljoni.add(paviljonIGK1);
                paviljoni.add(paviljonIGK2);
                paviljoni.add(paviljonIGK3);
                paviljoni.add(paviljonKPS1);
                paviljoni.add(paviljonKPS2);
                paviljoni.add(paviljonKPS3);

                HashSet<Soba> sobe = new HashSet<>();
                sobe.add(sobaIvica);
                sobe.add(sobaMarko);
                sobe.add(sobaPero);

                marko.setSoba(sobaMarko);
                sobaMarko.setStudent(marko);

                ivica.setSoba(sobaIvica);
                sobaIvica.setStudent(ivica);

                pero.setSoba(sobaPero);
                sobaPero.setStudent(pero);

                HashSet<Dom> domovi = new HashSet<>();
                domovi.add(radic);
                domovi.add(lascina);
                domovi.add(starcevic);
                domovi.add(igk);
                domovi.add(kps);
                domovi.add(cvjetno);

                sobaRepository.saveAll(sobe);
                domRepository.saveAll(domovi);
                paviljonRepository.saveAll(paviljoni);
                gradRepository.save(zagreb);
                gradRepository.save(osijek);
                oglasRepository.saveAll(oglasi);
                zaposlenikscRepository.save(stefko);
                studentskiCentarRepository.save(studentskiCentar);
                obavijestRepository.save(obavijestZaIvicu);
                trazeniUvjetiRepository.saveAll(trazeniUvjeti);
                studentRepository.saveAll(studentiZaSave);


                marko.setObavijesti(Collections.singletonList(obavijestZaIvicu));
                studentRepository.save(marko);
                System.out.println("Umetnute pocetne vrijednosti");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
