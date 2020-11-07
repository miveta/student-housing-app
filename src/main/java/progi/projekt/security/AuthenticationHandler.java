package progi.projekt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHandler {

	@Autowired
	private StudentUserDetailsService studentUserDetailsService;
	@Autowired
	private PasswordEncoder pswdEncoder;

	public Authentication authenticate (loginForm data) {

		String username = data.getLogin();
		String password = data.getPassword();

		UserDetails userDetails = studentUserDetailsService.loadUserByUsername(username);

		if (userDetails == null) {
			throw new BadCredentialsException("Username not provided");
		}
		if (!pswdEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Username or password is wrong");
		}

		Authentication auth =
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

		return auth;
	}

}
