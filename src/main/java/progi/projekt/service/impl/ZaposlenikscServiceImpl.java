package progi.projekt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import progi.projekt.model.Student;
import progi.projekt.model.ZaposlenikSC;
import progi.projekt.repository.ZaposlenikscRepository;
import progi.projekt.security.exception.JmbagNotFoundException;
import progi.projekt.security.exception.SavingException;
import progi.projekt.service.ZaposlenikscService;

import java.util.List;
import java.util.Optional;

@Service
public class ZaposlenikscServiceImpl implements ZaposlenikscService {
	@Autowired
	private ZaposlenikscRepository zaposlenikscRepository;

	@Override
	public List<ZaposlenikSC> listAll() {
		return zaposlenikscRepository.findAll();
	}

	@Override
	public ZaposlenikscRepository getRepo() {
		return zaposlenikscRepository;
	}

	@Override
	public Optional<ZaposlenikSC> findByEmail(String email) {
		try {
			Optional<ZaposlenikSC> opt = Optional.of(zaposlenikscRepository.findByEmail(email));
			return opt;
		} catch (Exception e) {
			//zaposelnikRepo baca exceptione koje mu proslijedi baza (e)?
			String originalMessage = e.getMessage();
			//throw new JmbagNotFoundException("No user with email: '" + email + "'");
			return Optional.empty();
		}
	}

	@Override
	public Optional<ZaposlenikSC> findBykorisnickoIme(String username) {
		try {
			Optional<ZaposlenikSC> opt = Optional.of(zaposlenikscRepository.findByKorisnickoIme(username));
			return opt;
		} catch (Exception e) {
			//studentRepo baca exceptione koje mu proslijedi baza (e)?
			String originalMessage = e.getMessage();
			//throw new UsernameNotFoundException("No user with username: '" + username + "'");
			return Optional.empty();
		}
	}

	@Override
	public String getLozinka(ZaposlenikSC zaposlenik) {
		return zaposlenik.getLozinka();
	}

	@Override
	public ZaposlenikSC createZaposlenikSC(ZaposlenikSC zaposlenik) throws SavingException {
		try {
			return zaposlenikscRepository.saveAndFlush(zaposlenik);
		} catch (Exception e) {
			//studentRepo baca exceptione koje mu proslijedi baza (e)?
			String originalMessage = e.getMessage();
			throw new SavingException("Exception while saving user. Original message: '" + originalMessage + "'");
		}
	}
}
