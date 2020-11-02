package progi.projekt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User; //ovo zamjeniti nasom klasom?
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import progi.projekt.model.Student;
import progi.projekt.service.StudentService;

import java.util.List;

import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;

@Service
public class StudentUserDetailsService implements UserDetailsService {
	@Value("${progi.projekt.admin.pass}")
	private String adminPasswordHash;


	@Autowired
	private StudentService studentService;

	@Override
	public UserDetails loadUserByUsername(String username) {
		return new User(username, password(username), authorities(username));
	}

	private String password(String username) throws UsernameNotFoundException{
		if (username.equals("admin"))
			return adminPasswordHash;

		//ako login nije hardcoded "admin" password dobijemo iz DB-a:
		try {
			Student student = studentService.findBykorisnickoIme(username);
			return studentService.getLozinka(student); }
		catch (UsernameNotFoundException e){
			//TODO
			String porukaIzStudSerImpl = e.getMessage();
			throw new UsernameNotFoundException("No user '" + username + "'");
			//ovoga ulovi SecurityExceptionHandler
		}
	}

	private List<GrantedAuthority> authorities(String username) throws UsernameNotFoundException {
		if (username.equals("admin"))
			return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");

		//ako login nije hardcoded "admin" role dobijemo iz DB-a:
		try {
			Student student = studentService.findBykorisnickoIme(username);
			if (studentService.isElevated(student)) //djelatnik SC-a i ls.
				return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN, ROLE_STUDENT");
			else
				return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_STUDENT");
		}
		catch (UsernameNotFoundException e){
			//TODO
			//return NO_AUTHORITIES;
			String porukaIzStudSerImpl = e.getMessage();
			throw new UsernameNotFoundException("No user '" + username + "'");
			//ovoga ulovi SecurityExceptionHandler
		}
	}

}

