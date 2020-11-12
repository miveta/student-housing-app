package progi.projekt.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import progi.projekt.model.Student;
import progi.projekt.model.ZaposlenikSC;
import progi.projekt.service.StudentService;
import progi.projekt.service.ZaposlenikSCService;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikUserDetailsService implements UserDetailsService {
	@Value("${progi.projekt.admin.pass}")
	private String adminPasswordHash;

	private final StudentService studentService;

	private final ZaposlenikSCService zaposlenikscService;

	public KorisnikUserDetailsService(StudentService studentService, ZaposlenikSCService zaposlenikscService) {
		this.studentService = studentService;
		this.zaposlenikscService = zaposlenikscService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			//region privremeni hardcoded login koji zaobidje bazu
			/*
            //privremeni hardcoded login koji zaobidje bazu
            if (username.equals("admin")) {
                String rolesZaposlenik = "ROLE_ADMIN, ROLE_ZAPOSLENIKSC, ROLE_STUDENT, ROLE_USER";
                return new User("admin", "pass",
                        AuthorityUtils.commaSeparatedStringToAuthorityList(rolesZaposlenik));
            } else if (username.equals("user")) {
                String rolesStudent = "ROLE_STUDENT, ROLE_USER";
                return new User("user", "pass",
                        AuthorityUtils.commaSeparatedStringToAuthorityList(rolesStudent));
            } else {
                throw new UsernameNotFoundException("No user '" + username + "'");
            }
 			*/
			//endregion

			return new User(username, password(username), authorities(username));
		} catch (Exception e) {
			//ovo ce uloviti exception od password() i authorities(), i ako constructor od Usera nesto baci
			throw new UsernameNotFoundException("Nema korisnika '" + username + "'");
		}

	}

	private String password(String username) throws UsernameNotFoundException {
		if (username.equals("admin"))
			return adminPasswordHash;

		//ako login nije hardcoded "admin", password dobijemo iz DB-a:
		try {
			Optional<Student> student = studentService.findBykorisnickoIme(username);
			if (student.isEmpty()) {
				Optional<ZaposlenikSC> zaposlenik = zaposlenikscService.findBykorisnickoIme(username);
				if (zaposlenik.isEmpty()) {
					throw new UsernameNotFoundException("Nema korisnika '" + username + "'");
				} else {
					return zaposlenikscService.getLozinka(zaposlenik.get());
				}
			} else {
				return studentService.getLozinka(student.get());
			}

		} catch (UsernameNotFoundException e) {
			String porukaIzStudSerImpl = e.getMessage();
			throw new UsernameNotFoundException("Nema korisnika '" + username + "'");
		}
	}

	private List<GrantedAuthority> authorities(String username) throws UsernameNotFoundException {
		if (username.equals("admin"))
			return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN, ROLE_ZAPOSLENIKSC");

		//ako login nije hardcoded "admin", role dobijemo iz DB-a:
		try {
			Optional<Student> student = studentService.findBykorisnickoIme(username);
			if (student.isEmpty()) {
				Optional<ZaposlenikSC> zaposlenik = zaposlenikscService.findBykorisnickoIme(username);
				if (zaposlenik.isEmpty()) {
					throw new UsernameNotFoundException("Nema korisnika '" + username + "'");
				} else {
					String rolesZaposlenik = "ROLE_ADMIN, ROLE_ZAPOSLENIKSC, ROLE_STUDENT, ROLE_USER";
					return AuthorityUtils.commaSeparatedStringToAuthorityList(rolesZaposlenik);
				}
			} else {
				String rolesStudent = "ROLE_STUDENT, ROLE_USER";
				return AuthorityUtils.commaSeparatedStringToAuthorityList(rolesStudent);
			}

		} catch (UsernameNotFoundException e) {
			String porukaIzStudSerImpl = e.getMessage();
			throw new UsernameNotFoundException("Nema korisnika '" + username + "'");
		}
	}

}
