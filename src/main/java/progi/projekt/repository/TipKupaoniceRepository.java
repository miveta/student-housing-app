package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.TipKupaonice;

public interface TipKupaoniceRepository extends JpaRepository<TipKupaonice, Long> {
    public TipKupaonice findByTip(String tip);
}
