package progi.projekt.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import progi.projekt.model.ZaposlenikSC;
import progi.projekt.repository.ZaposlenikscRepository;
import progi.projekt.security.exception.SavingException;

import java.util.List;
import java.util.Optional;

public interface ZaposlenikscService {
    List<ZaposlenikSC> listAll();

    ZaposlenikscRepository getRepo();

    Optional<ZaposlenikSC> findByEmail(String email) throws UsernameNotFoundException;

    Optional<ZaposlenikSC> findBykorisnickoIme(String username) throws UsernameNotFoundException;

    String getLozinka(ZaposlenikSC zaposlenik);

    ZaposlenikSC createZaposlenikSC(ZaposlenikSC zaposlenik) throws SavingException;
}
