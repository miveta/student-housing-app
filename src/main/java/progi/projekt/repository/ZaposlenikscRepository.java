package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progi.projekt.model.ZaposlenikSC;

@Repository
public interface ZaposlenikscRepository extends JpaRepository<ZaposlenikSC, Long> {
    ZaposlenikSC findByEmail(String email);

    ZaposlenikSC findByKorisnickoIme(String korisnickoIme);
}
