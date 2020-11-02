package progi.projekt.security;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity //ukljucivanje provjere razine pristupa
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
		http.httpBasic();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/").permitAll();
		http.headers().frameOptions().sameOrigin(); //za h2, nama ne treba?
		http.csrf().disable();
		super.configure(http);
	}
}

//dodati @Secured("ROLE_ADMIN") na metode koje poziva admin, @Secured("ROLE_STUDENT") na metode koje poziva student
